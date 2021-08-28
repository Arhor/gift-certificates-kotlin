plugins {
    id("war")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.allopen")
}

group = "com.epam.esm"
version  = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
    targetCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
}

dependencies {
    kapt("org.springframework:spring-context-indexer:${Versions.spring}")

    compileOnly("javax.servlet:javax.servlet-api:${Versions.servlet}")

    implementation(project(":service"))

    implementation("ch.qos.logback:logback-core:${Versions.logback}")
    implementation("ch.qos.logback:logback-classic:${Versions.logback}")

    implementation("com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jackson}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework:spring-context:${Versions.spring}")
    implementation("org.springframework:spring-context-support:${Versions.spring}")
    implementation("org.springframework:spring-webmvc:${Versions.spring}")

    implementation("io.github.microutils:kotlin-logging-jvm:${Versions.kotlinLogging}")
}

allOpen {
    annotations(
        "org.springframework.context.annotation.Configuration",
        "org.springframework.stereotype.Component",
        "org.springframework.web.bind.annotation.Controller",
        "org.springframework.web.bind.annotation.RestController",
        "org.springframework.web.bind.annotation.RestControllerAdvice",
    )
}
