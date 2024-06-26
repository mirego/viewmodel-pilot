@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("buildlogic.kotlin.multiplatform")
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
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlin.test.junit)
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.assertk)
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
