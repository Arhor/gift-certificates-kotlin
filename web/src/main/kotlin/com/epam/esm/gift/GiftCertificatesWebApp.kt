package com.epam.esm.gift

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(proxyBeanMethods = false)
class GiftCertificatesWebApp

fun main(args: Array<String>) {
    runApplication<GiftCertificatesWebApp>(*args)
}