package com.epam.esm.gift.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScan.Filter
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@Configuration
@ComponentScan(
    basePackages = ["com.epam.esm.gift"],
    excludeFilters = [
        Filter(EnableWebMvc::class),
        Filter(RestController::class),
    ]
)
class RootConfig