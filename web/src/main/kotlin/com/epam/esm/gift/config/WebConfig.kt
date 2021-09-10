package com.epam.esm.gift.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@EnableWebMvc
@Configuration(proxyBeanMethods = false)
@ComponentScan("com.epam.esm.gift.web")
class WebConfig : WebMvcConfigurer {

    @Bean
    fun messageSource(): MessageSource {
        return ResourceBundleMessageSource().apply {
            setBasename("messages")
        }
    }

    override fun extendMessageConverters(converters: List<HttpMessageConverter<*>?>) {
        for (converter in converters) {
            if (converter is MappingJackson2HttpMessageConverter) {
                converter.objectMapper.apply {
                    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    setSerializationInclusion(JsonInclude.Include.NON_NULL)
                }
            }
        }
    }
}