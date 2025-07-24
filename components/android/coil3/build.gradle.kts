@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("buildlogic.android.library")
    alias(libs.plugins.compose.compiler)
}

group = "com.mirego.pilot"

android {
    namespace = "com.mirego.pilot.components.coil3"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.components)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.coil3.compose)
    implementation(libs.coil3.network.okhttp)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                artifactId = "components-coil3"
            }
        }
    }
}
