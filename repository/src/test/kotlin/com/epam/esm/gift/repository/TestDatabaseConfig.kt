package com.epam.esm.gift.repository

import com.epam.esm.gift.TimeService
import com.epam.esm.gift.config.RepositoryConfig
import mu.KLogging
import org.flywaydb.core.Flyway
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import java.time.Instant
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Import(RepositoryConfig::class)
@Configuration(proxyBeanMethods = false)
internal class TestDatabaseConfig {

    @Bean
    fun simpleTimeService(): TimeService {

        return TimeService { Instant.now() }
    }

    @Bean
    fun dataSource(): DataSource {

        logger.debug { "Creating DataSource instance" }
        return DriverManagerDataSource().apply {
            url = db.jdbcUrl
            username = db.username
            password = db.password
            setDriverClassName(db.driverClassName)
        }
    }

    @Bean
    fun jpaVendorAdapter(): JpaVendorAdapter {

        logger.debug { "Creating JpaVendorAdapter (Hibernate)" }
        return HibernateJpaVendorAdapter().apply {
            setDatabase(Database.POSTGRESQL)
            setShowSql(true)
            setGenerateDdl(false)
            setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect")
        }
    }

    @Bean
    fun entityManagerFactoryBean(
        dataSource: DataSource,
        jpaVendorAdapter: JpaVendorAdapter
    ): LocalContainerEntityManagerFactoryBean {

        logger.debug { "Creating LocalContainerEntityManagerFactoryBean instance" }
        return LocalContainerEntityManagerFactoryBean().apply {
            setDataSource(dataSource)
            setJpaVendorAdapter(jpaVendorAdapter)
            setPackagesToScan("com.epam.esm")
        }
    }

    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {

        logger.debug { "Creating TransactionManager instance" }
        return JpaTransactionManager(entityManagerFactory)
    }


    @Bean(initMethod = "migrate")
    fun flyway(dataSource: DataSource): Flyway {

        logger.debug { "Configuring flyway instance to apply migrations" }
        return Flyway.configure().dataSource(dataSource).load()
    }

    companion object : KLogging() {
        @JvmStatic
        @Container
        private val db = PostgreSQLContainer<Nothing>("postgres:11.7").apply { start() }
    }
}