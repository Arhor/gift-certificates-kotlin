package com.epam.esm.gift.repository.impl

import com.epam.esm.gift.model.Auditable
import com.epam.esm.gift.model.Entity
import com.epam.esm.gift.repository.Repository
import com.epam.esm.gift.repository.bootstrap.EntityModel
import com.epam.esm.gift.repository.bootstrap.Queries
import com.epam.esm.gift.repository.bootstrap.QueryProvider
import com.epam.esm.gift.repository.bootstrap.RepositoryIntrospector
import mu.KLogging
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import java.io.Serializable
import kotlin.system.measureTimeMillis

abstract class AbstractRepository<T, K>(
    protected val rowMapper: RowMapper<T>,
) : Repository<T, K>, InitializingBean where T : Entity<K>, K : Serializable {

    @Autowired
    protected lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    @Autowired
    private lateinit var introspector: RepositoryIntrospector

    @Autowired
    private lateinit var queryProvider: QueryProvider

    protected lateinit var entityModel: EntityModel
    protected lateinit var queries: Queries

    override fun afterPropertiesSet() {
        val bootstrapTime = measureTimeMillis {
            entityModel = introspector.introspect(this::class)
            queries = queryProvider.buildQueries(entityModel)
        }
        logger.info { "Bootstrapped ${this::class.simpleName} in $bootstrapTime milliseconds" }
    }

    @Suppress("UNCHECKED_CAST")
    override fun create(entity: T): T {
        if (entity is Auditable) {
            entity.onCreate()
        }
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(queries.insertOne, BeanPropertySqlParameterSource(entity), keyHolder)
        entity.id = keyHolder.keys?.get("id") as K
        return entity
    }

    override fun update(entity: T): T {
        if (entity is Auditable) {
            entity.onUpdate()
        }
        return when (jdbcTemplate.update(queries.updateOne, BeanPropertySqlParameterSource(entity))) {
            1 -> entity
            else -> throw RuntimeException("Number of updated rows is not equal to 1")
        }
    }

    override fun findAll(): List<T> {
        return jdbcTemplate.query(queries.selectAll, rowMapper)
    }

    override fun findById(id: K): T? {
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

    companion object : KLogging()
}