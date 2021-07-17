package com.epam.esm.gift.repository

import com.epam.esm.gift.model.BaseEntity
import com.epam.esm.gift.repository.bootstrap.Queries
import com.epam.esm.gift.repository.bootstrap.QueryProvider
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import java.io.Serializable

abstract class AbstractBaseRepository<T, K>(
    private val rowMapper: RowMapper<T>,
) : BaseRepository<T, K>, InitializingBean where T : BaseEntity<K>, K : Serializable {

    @Autowired
    private lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    @Autowired
    private lateinit var queryProvider: QueryProvider

    private lateinit var queries: Queries

    override fun afterPropertiesSet() {
        queries = queryProvider.buildQueries(this::class)
    }

    @Suppress("UNCHECKED_CAST")
    override fun create(entity: T): T {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(queries.insertOne, BeanPropertySqlParameterSource(entity), keyHolder)
        entity.id = keyHolder.keys?.get("id") as K
        return entity
    }

    override fun update(entity: T): T {
        return when (jdbcTemplate.update(queries.updateOne, BeanPropertySqlParameterSource(entity))) {
            1 -> entity
            else -> throw RuntimeException("Number of updated rows is not equal to 1")
        }
    }

    override fun readAll(): List<T> {
        return jdbcTemplate.query(queries.selectAll, rowMapper)
    }

    override fun readById(id: K): T? {
        val params = mapOf("id" to id)
        return jdbcTemplate.query(queries.selectOne, params, rowMapper).firstOrNull()
    }

    override fun delete(entity: T) {
        entity.id?.let { deleteById(it) }
    }

    override fun deleteById(id: K) {
        val params = mapOf("id" to id)
        jdbcTemplate.update(queries.deleteOne, params)
    }
}