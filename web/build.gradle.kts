plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.allopen")
}

java {
    sourceCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
    targetCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
}

dependencies {
    implementation(project(":service"))

    implementation("com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jackson}")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework:spring-context:${Versions.spring}")
    implementation("org.springframework:spring-webmvc:${Versions.spring}")

    implementation("io.github.microutils:kotlin-logging-jvm:${Versions.kotlinLogging}")
}

allOpen {
    annotations(
        "org.springframework.context.annotation.Configuration",
        "org.springframework.stereotype.Component",
        "org.springframework.stereotype.Repository"
    )
}
