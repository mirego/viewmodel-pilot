@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("buildlogic.android.library")
    alias(libs.plugins.compose.compiler)
}

group = "com.mirego.pilot"

android {
    namespace = "com.mirego.pilot.components.material3"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.components)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.compose.material3)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                artifactId = "components-material3"
            }
        }
    }
}
