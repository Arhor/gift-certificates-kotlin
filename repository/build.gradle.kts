plugins {
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
    targetCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.slf4j:slf4j-api:${Versions.slf4j}")
    implementation("io.github.microutils:kotlin-logging-jvm:${Versions.kotlinLogging}")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.assertj:assertj-core:${Versions.assertJCore}")
    testImplementation("org.mockito:mockito-core:${Versions.mockitoCore}")
    testImplementation("org.mockito:mockito-junit-jupiter:${Versions.mockitoCore}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${Versions.junitJupiter}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.junitJupiter}")
    testImplementation("org.testcontainers:junit-jupiter:${Versions.testcontainers}")
    testImplementation("org.testcontainers:postgresql:${Versions.testcontainers}")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.junitJupiter}")
}
