plugins {
    id("org.flywaydb.flyway")
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

    implementation("ch.qos.logback:logback-core:${Versions.logback}")
    implementation("ch.qos.logback:logback-classic:${Versions.logback}")

    implementation("com.zaxxer:HikariCP:${Versions.hikariCP}")

    implementation("io.github.microutils:kotlin-logging-jvm:${Versions.kotlinLogging}")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.slf4j:slf4j-api:${Versions.slf4j}")

    implementation("org.springframework:spring-context:${Versions.spring}")
    implementation("org.springframework:spring-jdbc:${Versions.spring}")

    runtimeOnly("org.postgresql:postgresql:${Versions.postgresql}")

    testImplementation("org.assertj:assertj-core:${Versions.assertJCore}")
    testImplementation("org.flywaydb:flyway-core:${Versions.flyway}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${Versions.junitJupiter}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.junitJupiter}")
    testImplementation("org.mockito:mockito-core:${Versions.mockitoCore}")
    testImplementation("org.mockito:mockito-junit-jupiter:${Versions.mockitoCore}")
    testImplementation("org.springframework:spring-test:${Versions.spring}")
    testImplementation("org.testcontainers:junit-jupiter:${Versions.testcontainers}")
    testImplementation("org.testcontainers:postgresql:${Versions.testcontainers}")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.junitJupiter}")
}

allOpen {
    annotations(
        "org.springframework.context.annotation.Configuration",
        "org.springframework.stereotype.Component",
        "org.springframework.stereotype.Repository",
        "org.springframework.transaction.annotation.Transactional"
    )
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
    withType<Test> {
        useJUnitPlatform()
    }
}
