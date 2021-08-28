plugins {
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
    kapt("org.mapstruct:mapstruct-processor:${Versions.mapstruct}")
    kapt("org.springframework:spring-context-indexer:${Versions.spring}")

    implementation(project(":repository"))

    implementation("ch.qos.logback:logback-core:${Versions.logback}")
    implementation("ch.qos.logback:logback-classic:${Versions.logback}")

    implementation("io.github.microutils:kotlin-logging-jvm:${Versions.kotlinLogging}")

    implementation("org.mapstruct:mapstruct:${Versions.mapstruct}")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.slf4j:slf4j-api:${Versions.slf4j}")

    implementation("org.springframework:spring-context:${Versions.spring}")
    implementation("org.springframework:spring-tx:${Versions.spring}")

    testImplementation("org.assertj:assertj-core:${Versions.assertJCore}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${Versions.junitJupiter}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.junitJupiter}")
    testImplementation("org.mockito:mockito-core:${Versions.mockitoCore}")
    testImplementation("org.mockito:mockito-junit-jupiter:${Versions.mockitoCore}")
    testImplementation("org.springframework:spring-test:${Versions.spring}")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.junitJupiter}")
}

allOpen {
    annotations(
        "org.springframework.context.annotation.Configuration",
        "org.springframework.stereotype.Component",
        "org.springframework.stereotype.Service",
        "org.springframework.transaction.annotation.Transactional"
    )
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}
