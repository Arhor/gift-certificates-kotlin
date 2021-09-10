plugins {
    id("java-library")
}

group = "com.epam.esm"
version = "0.0.1"

dependencies {
    implementation("com.google.code.findbugs:jsr305:${Versions.findbugsJsr305}")
}