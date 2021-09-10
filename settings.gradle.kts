rootProject.name = "gift-certificates-kotlin"

include(":common")
include(":repository")
include(":service")
include(":web")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("postgresql",  "42.2.23")
        }
        create("tools") {
            version("java",   "11")
            version("kotlin", "1.5.30")
            version("gradle", "7.2")
        }
    }
}
