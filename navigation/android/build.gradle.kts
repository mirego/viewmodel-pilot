@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("buildlogic.android.library")
    alias(libs.plugins.kotlinx.binaryCompatibilityValidator)
    alias(libs.plugins.mirego.publish)
}

android {
    namespace = "com.mirego.viewmodel.pilot.navigation.android"
    defaultConfig {
        vectorDrawables {
            useSupportLibrary = true
        }

        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
            freeCompilerArgs =
                listOf(
                    "-opt-in=kotlin.time.ExperimentalTime",
                    "-opt-in=kotlin.ExperimentalStdlibApi",
                    "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                    "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                )
        }
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
        resources.excludes.addAll(
            listOf(
                "META-INF/**",
            ),
        )
    }

    lint {
        abortOnError = true
        checkReleaseBuilds = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }
    sourceSets.configureEach {
        java.srcDirs("src/$name/kotlin")
    }
}

repositories {
    mavenCentral()
    maven("https://s3.amazonaws.com/mirego-maven/public")
}

dependencies {
    implementation(projects.navigationCommon)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.runtime)
    implementation(libs.androidx.navigation.compose)
}
