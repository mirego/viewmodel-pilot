@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("buildlogic.kotlin.multiplatform")
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlinx.binaryCompatibilityValidator)
    alias(libs.plugins.mirego.publish)
}

group = "com.mirego.pilot"

ktlint {
    enableExperimentalRules.set(true)
    filter {
        exclude { it.file.path.contains("${layout.buildDirectory}/generated/") }
    }
}

android {
    namespace = "com.mirego.pilot.navigation"

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.kotlin.stdlib.jdk8)
                implementation(libs.bundles.lifecycle)
                implementation(libs.androidx.compose.ui)
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.navigation.runtime)
                implementation(libs.androidx.navigation.compose)
            }
        }
    }
}
