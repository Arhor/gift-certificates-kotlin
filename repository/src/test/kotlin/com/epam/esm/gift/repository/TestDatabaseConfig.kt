package com.epam.esm.gift.repository

import mu.KotlinLogging
import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.transaction.TransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import javax.sql.DataSource

private typealias PostgresContainer = PostgreSQLContainer<*>

private val log = KotlinLogging.logger {}

@Configuration(proxyBeanMethods = false)
@ComponentScan("com.epam.esm.gift.repository")
@EnableTransactionManagement
internal class TestDatabaseConfig {

    @Bean
    fun dataSource(): DataSource {
        log.debug("Creating DataSource instance")
        return DriverManagerDataSource().apply {
            url = db.jdbcUrl
            username = db.username
            password = db.password
            setDriverClassName(db.driverClassName)
        }
    }

    @Bean
    fun transactionManager(dataSource: DataSource): TransactionManager {
        log.debug("Creating TransactionManager to handle DB transactions")
        return DataSourceTransactionManager(dataSource)
    }

    @Bean
    fun jdbcTemplate(dataSource: DataSource): NamedParameterJdbcTemplate {
        log.debug("Creating JdbcTemplate to execute queries")
        return NamedParameterJdbcTemplate(dataSource)
    }


    @Bean(initMethod = "migrate")
    fun flyway(dataSource: DataSource): Flyway {
        log.debug("Configuring flyway instance to apply migrations")
        return Flyway.configure().dataSource(dataSource).load()
    }

    companion object {
        @JvmStatic
        @Container
        private val db = PostgresContainer("postgres:11.7").apply { start() }
    }
}