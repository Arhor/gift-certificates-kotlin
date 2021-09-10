plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.spring")
}

version  = "0.0.1"
description = "web"

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
    kapt("org.springframework:spring-context-indexer")

    implementation(project(":common"))
    implementation(project(":service"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.github.microutils:kotlin-logging-jvm:${Versions.kotlinLogging}")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}
