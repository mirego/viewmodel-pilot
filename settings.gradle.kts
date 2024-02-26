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
    ":navigation-common",
    ":navigation-android",
    ":viewmodel-common",
)

project(":navigation-common").projectDir = File("navigation/common")
project(":navigation-android").projectDir = File("navigation/android")
project(":viewmodel-common").projectDir = File("viewmodel/common")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
