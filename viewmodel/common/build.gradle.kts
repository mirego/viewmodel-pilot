@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("buildlogic.kotlin.multiplatform")
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlinx.binaryCompatibilityValidator)
    alias(libs.plugins.mirego.publish)
}

group = "com.mirego.pilot"

android {
    namespace = "com.mirego.pilot.viewmodel"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }

        androidMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodel.ktx)
        }
    }
}
