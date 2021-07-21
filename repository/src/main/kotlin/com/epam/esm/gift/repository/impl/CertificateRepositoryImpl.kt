package com.epam.esm.gift.repository.impl

import com.epam.esm.gift.model.Certificate
import com.epam.esm.gift.repository.CertificateRepository
import com.epam.esm.gift.repository.bootstrap.ColumnProperty
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class CertificateRepositoryImpl(rowMapper: RowMapper<Certificate>) :
    AbstractRepository<Certificate, Long>(rowMapper), CertificateRepository {

    override fun updatePartially(certificateId: Long, diffs: List<Pair<ColumnProperty, Any?>>) {
        if (diffs.isNotEmpty()) {

            for ((column, _) in diffs) {
                if (!entityModel.restColumns.contains(column)) {
                    throw IllegalArgumentException(
                        "Property ${column.propName} does not belong to the Certificate entity"
                    )
                }
                if (entityModel.idColumn == column) {
                    throw IllegalArgumentException(
                        "It is forbidden to modify ID property: ${column.propName}"
                    )
                }
            }

            val params = mapOf("certificateId" to certificateId) + diffs.map { (column, value) ->
                column.propName to value
            }

            jdbcTemplate.update(
                """
                UPDATE certificates
                SET ${diffs.joinToString { (column, _) -> "${column.realName} = :${column.propName}" }}
                WHERE id = :certificateId
                """.trimIndent(),
                params
            )
        }
    }
}