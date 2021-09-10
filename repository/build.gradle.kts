plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.flywaydb.flyway")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.jetbrains.kotlin.plugin.jpa")
}

version  = "1.0-SNAPSHOT"
description = "repository"

java {
    sourceCompatibility = JavaVersion.toVersion(Versions.java)
    targetCompatibility = JavaVersion.toVersion(Versions.java)
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${Versions.testcontainers}")
    }
}

configurations {
    testImplementation {
        exclude(module = "junit-vintage-engine")
    }
}

dependencies {
    kapt("org.springframework:spring-context-indexer")

    implementation(project(":common"))

    implementation("io.github.microutils:kotlin-logging-jvm:${Versions.kotlinLogging}")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
}

flyway {
    url = System.getenv("JDBC_DATABASE_URL") ?: "jdbc:postgresql://localhost:5432/gift_certificates"
    user = System.getenv("JDBC_DATABASE_USERNAME") ?: "postgres"
    password = System.getenv("JDBC_DATABASE_PASSWORD") ?: "password"

    driver = "org.postgresql.Driver"
    encoding = "UTF-8"
    locations = arrayOf("classpath:db/migration")
}

tasks {
    bootJar {
        enabled = false
    }

    jar {
        enabled = true
    }

    withType<Test> {
        useJUnitPlatform()
    }
}
