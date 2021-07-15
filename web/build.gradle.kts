plugins {
    id ("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
    targetCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
}

dependencies {
    implementation (project(":service"))
    implementation ("org.jetbrains.kotlin:kotlin-stdlib")
}
