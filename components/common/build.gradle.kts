@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("buildlogic.kotlin.multiplatform")
}

group = "com.mirego.pilot"

android {
    namespace = "com.mirego.pilot.components"

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
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
