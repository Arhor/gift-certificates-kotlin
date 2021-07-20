package com.epam.esm.gift.repository

import com.epam.esm.gift.model.Tag
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TagRepositoryImpl(rowMapper: RowMapper<Tag>) : AbstractBaseRepository<Tag, Long>(rowMapper), TagRepository {

    override fun findTagByName(name: String): Tag? {
        val params = mapOf("name" to name)
        return jdbcTemplate.query("${queries.selectAll} WHERE name = :name", params, rowMapper).firstOrNull()
    }

    override fun findTagByNames(names: List<String>): List<Tag> {
        val params = mapOf("names" to names)
        return jdbcTemplate.query("${queries.selectAll} WHERE name IN (:names)", params, rowMapper)
    }

    override fun findTagsByCertificateId(certificateId: Long): List<Tag> {
        return jdbcTemplate.query(
            """
            SELECT t.id, t.name
            FROM tags t
            JOIN certificates_has_tags cht on t.id = cht.tags_id
            WHERE cht.certificates_id = :certificateId
            """.trimIndent(),
            mapOf("certificateId" to certificateId),
            rowMapper
        )
    }

    override fun addTagsToCertificate(certificateId: Long, tags: List<Tag>) {
        if (tags.isEmpty()) {
            return
        }

        val params = mutableMapOf("certificateId" to certificateId)

        val query = StringJoiner(
            ", ",
            "INSERT INTO certificates_has_tags (certificates_id, tags_id) VALUES ",
            ""
        )

        for ((index, tag) in tags.withIndex()) {
            val tagIdPlaceholder = "tag$index"
            query.add("(:certificateId, :$tagIdPlaceholder)")
            params[tagIdPlaceholder] = tag.id!!
        }

        jdbcTemplate.update(query.toString(), params)
    }

    override fun removeTagsFromCertificate(certificateId: Long, tags: List<Tag>) {
        jdbcTemplate.update(
            """
            DELETE
            FROM certificates_has_tags cht
            WHERE cht.certificates_id = :certificateId
            AND cht.tags_id IN (:tagIds) 
            """.trimIndent(),
            mapOf(
                "certificateId" to certificateId,
                "tagIds" to tags.mapNotNull { it.id }
            )
        )
    }
}