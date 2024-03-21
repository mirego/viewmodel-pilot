import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.mirego.viewmodel.pilot.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.kotlinx.binaryCompatibilityValidator)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "buildlogic.android.library"
            implementationClass = "com.mirego.viewmodel.pilot.AndroidLibraryConventionPlugin"
        }
        register("kotlinMultiplatform") {
            id = "buildlogic.kotlin.multiplatform"
            implementationClass = "com.mirego.viewmodel.pilot.KotlinMultiplatformConventionPlugin"
        }
    }
}
