-- START >>> table 'tags'
CREATE TABLE IF NOT EXISTS tags
(
    id   BIGSERIAL   NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL UNIQUE
) WITH (OIDS = FALSE);
-- END <<< table 'tags'

-- START >>> table 'certificates'
CREATE TABLE IF NOT EXISTS certificates
(
    id                BIGSERIAL      NOT NULL PRIMARY KEY,
    name              VARCHAR(100)   NOT NULL,
    description       VARCHAR(2000)  NULL,
    price             NUMERIC(12, 2) NOT NULL,
    date_time_created TIMESTAMP      NOT NULL,
    date_time_updated TIMESTAMP      NULL,
    duration          INT            NOT NULL
) WITH (OIDS = FALSE);
-- END <<< table 'certificates'

-- START >>> table 'certificates_has_tags'
CREATE TABLE IF NOT EXISTS certificates_has_tags
(
    certificates_id BIGINT NOT NULL
        CONSTRAINT certificates_FK REFERENCES certificates
            ON UPDATE CASCADE
            ON DELETE CASCADE,

    tags_id         BIGINT NOT NULL
        CONSTRAINT tags_FK REFERENCES tags
            ON UPDATE CASCADE
            ON DELETE CASCADE,

    CONSTRAINT certificates_has_tags_pk PRIMARY KEY (certificates_id, tags_id)
) WITH (OIDS = FALSE);
-- END <<< table 'certificates_has_tags'
