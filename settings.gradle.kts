rootProject.name = "gift-certificates-kotlin"

include(
    ":repository",
    ":service",
    ":web",
    ":client",
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
