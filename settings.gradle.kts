pluginManagement {
    repositories {
        includeBuild("build-logic")
        mavenLocal()
        gradlePluginPortal()
        google()
        maven(url = "https://s3.amazonaws.com/mirego-maven/public")
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "mirego") {
                useModule("mirego:${requested.id.name}-plugin:${requested.version}")
            }
        }
    }
}

rootProject.name = "viewmodel-pilot"

include(
    ":navigation",
    ":viewmodel",
)

project(":navigation").projectDir = File("navigation/common")
project(":viewmodel").projectDir = File("viewmodel/common")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
