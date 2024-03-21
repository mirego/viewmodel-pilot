@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("buildlogic.android.library")
}

group = "com.mirego.pilot"

android {
    namespace = "com.mirego.pilot.components.material3"

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
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
