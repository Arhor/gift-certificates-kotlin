-- START >>> table 'certificates'
CREATE EXTENSION IF NOT EXISTS plpgsql;
CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX IF NOT EXISTS certificates_date_time_updated_IDX
    ON certificates (date_time_updated);

CREATE INDEX IF NOT EXISTS certificates_description_gin_trgm_IDX
    ON certificates USING gin (description public.gin_trgm_ops);

ALTER TABLE IF EXISTS certificates
    ADD COLUMN IF NOT EXISTS
        deleted BOOLEAN NOT NULL DEFAULT false;
-- END <<< table 'certificates'

-- START >>> table 'users'
CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL   NOT NULL PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(72) NOT NULL,
    role     VARCHAR(20) NOT NULL
) WITH (OIDS = FALSE);
-- END <<< table 'users'

-- START >>> table 'orders'
CREATE TABLE IF NOT EXISTS orders
(
    id                BIGSERIAL NOT NULL PRIMARY KEY,
    user_id           BIGINT    NOT NULL,
    date_time_created TIMESTAMP NOT NULL,
    date_time_updated TIMESTAMP NULL,

    CONSTRAINT users_FK
        FOREIGN KEY (user_id) REFERENCES users
            ON UPDATE CASCADE
            ON DELETE CASCADE
) WITH (OIDS = FALSE);
-- END <<< table 'orders'

-- START >>> table 'orders_has_certificates'
CREATE TABLE IF NOT EXISTS orders_has_certificates
(
    order_id       BIGINT         NOT NULL,
    certificate_id BIGINT         NOT NULL,
    actual_price   NUMERIC(12, 2) NOT NULL,
    amount         SMALLINT       NOT NULL,

    CONSTRAINT orders_has_certificates_PK
        PRIMARY KEY (order_id, certificate_id),

    CONSTRAINT orders_FK
        FOREIGN KEY (order_id) REFERENCES orders
            ON UPDATE CASCADE
            ON DELETE CASCADE,

    CONSTRAINT certificates_FK
        FOREIGN KEY (certificate_id) REFERENCES certificates
            ON UPDATE CASCADE
            ON DELETE CASCADE
) WITH (OIDS = FALSE);
-- END <<< table 'orders_has_certificates'

-- START >>> procedure 'FIND_RICH_USERS'
CREATE OR REPLACE FUNCTION FIND_RICH_USERS() RETURNS TEXT AS
$BODY$
DECLARE
    max_sum NUMERIC;
BEGIN
    SELECT max_sum = SUM(crt.price)
    FROM users usr
             INNER JOIN orders ord
                        ON usr.id = ord.user_id
             INNER JOIN orders_has_certificates ohc
                        ON ord.id = ohc.order_id
             INNER JOIN certificates crt
                        ON ohc.certificate_id = crt.id
    GROUP BY usr.id
    ORDER BY SUM(crt.price) DESC
    LIMIT 1;

    RETURN (
        SELECT STRING_AGG(rich_users.username, ', ')
        FROM (
                 SELECT usr.username
                 FROM users usr
                          INNER JOIN orders ord
                                     ON usr.id = ord.user_id
                          INNER JOIN orders_has_certificates ohc
                                     ON ord.id = ohc.order_id
                          INNER JOIN certificates crt
                                     ON ohc.certificate_id = crt.id
                 GROUP BY usr.id
                 HAVING SUM(crt.price) = max_sum
             ) AS rich_users
    );
END;
$BODY$ LANGUAGE plpgsql IMMUTABLE;
-- END <<< procedure 'FIND_RICH_USERS'

