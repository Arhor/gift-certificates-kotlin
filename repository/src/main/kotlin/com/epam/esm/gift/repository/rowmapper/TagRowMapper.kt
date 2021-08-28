package com.epam.esm.gift.repository.rowmapper

import com.epam.esm.gift.model.Tag
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class TagRowMapper : RowMapper<Tag> {

    override fun mapRow(rs: ResultSet, rowNum: Int): Tag {
        // @formatter:off
        return Tag(
            id   = rs.getLong("id"),
            name = rs.getString("name")
        )
        // @formatter:on
    }
}