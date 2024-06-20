@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
    id("buildlogic.kotlin.multiplatform")
    alias(libs.plugins.compose.compiler)
}

group = "com.mirego.pilot"

ktlint {
    enableExperimentalRules.set(true)
    filter {
        exclude { it.file.path.contains("${layout.buildDirectory}/generated/") }
    }
}

composeCompiler {
    targetKotlinPlatforms.set(setOf(KotlinPlatformType.androidJvm))
}

android {
    namespace = "com.mirego.pilot.navigation"

    buildFeatures {
        compose = true
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
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.assertk)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.kotlin.test.junit)
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
