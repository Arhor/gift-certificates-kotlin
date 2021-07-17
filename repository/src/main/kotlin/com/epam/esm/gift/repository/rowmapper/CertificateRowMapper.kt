package com.epam.esm.gift.repository.rowmapper

import com.epam.esm.gift.model.Certificate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class CertificateRowMapper : RowMapper<Certificate> {

    override fun mapRow(rs: ResultSet, rowNum: Int): Certificate {
        // @formatter:off
        return Certificate(
            id              = rs.getLong("id"),
            name            = rs.getString("name"),
            description     = rs.getString("description"),
            price           = rs.getBigDecimal("price"),
            duration        = rs.getInt("duration"),
            dateTimeCreated = rs.getTimestamp("date_time_created")?.toLocalDateTime(),
            dateTimeUpdated = rs.getTimestamp("date_time_updated")?.toLocalDateTime()
        )
        // @formatter:on
    }
}