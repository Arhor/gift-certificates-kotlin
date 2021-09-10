package com.epam.esm.gift.config

import com.epam.esm.gift.TimeService
import com.epam.esm.gift.config.RepositoryConfig.Companion.DATE_TIME_PROVIDER_BEAN
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*

@Configuration(proxyBeanMethods = false)
@ComponentScan("com.epam.esm.gift.repository")
@EntityScan("com.epam.esm.gift.model")
@EnableJpaAuditing(
    modifyOnCreate = false,
    dateTimeProviderRef = DATE_TIME_PROVIDER_BEAN
)
@EnableTransactionManagement
class RepositoryConfig {

    @Bean(DATE_TIME_PROVIDER_BEAN)
    fun dateTimeProvider(timeService: TimeService): DateTimeProvider {
        return DateTimeProvider { Optional.of(timeService.now()) }
    }

    companion object {
        const val DATE_TIME_PROVIDER_BEAN = "timeServiceDelegatingDateTimeProvider"
    }
}