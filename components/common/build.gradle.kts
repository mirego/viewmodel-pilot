@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
    id("buildlogic.kotlin.multiplatform")
    alias(libs.plugins.compose.compiler)
}

group = "com.mirego.pilot"

composeCompiler {
    targetKotlinPlatforms.set(setOf(KotlinPlatformType.androidJvm))
}

android {
    namespace = "com.mirego.pilot.components"

    buildFeatures {
        compose = true
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.viewmodel)
            implementation(libs.kotlinx.coroutines.core)
        }

        androidMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.compose.foundation)
            implementation(libs.androidx.compose.ui)
            implementation(libs.androidx.lifecycle.common)
        }
    }
}
