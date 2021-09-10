plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.spring")
}

version  = "0.0.1"
description = "service"

java {
    sourceCompatibility = JavaVersion.toVersion(Versions.java)
    targetCompatibility = JavaVersion.toVersion(Versions.java)
}

configurations {
    testImplementation {
        exclude(module = "junit-vintage-engine")
    }
}

dependencies {
    kapt("org.mapstruct:mapstruct-processor:${Versions.mapstruct}")
    kapt("org.springframework:spring-context-indexer")

    implementation(project(":repository"))

    implementation("io.github.microutils:kotlin-logging-jvm:${Versions.kotlinLogging}")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.mapstruct:mapstruct:${Versions.mapstruct}")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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
