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
    ":components",
    ":components-coil",
    ":components-material3",
)

project(":navigation").projectDir = File("navigation/common")
project(":viewmodel").projectDir = File("viewmodel/common")
project(":components").projectDir = File("components/common")
project(":components-coil").projectDir = File("components/android/coil")
project(":components-material3").projectDir = File("components/android/material3")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
