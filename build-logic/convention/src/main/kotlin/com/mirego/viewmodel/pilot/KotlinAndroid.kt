package com.mirego.viewmodel.pilot

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

private object Constants {
    val JAVA_VERSION = JavaVersion.VERSION_17
}

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = 34

        defaultConfig {
            minSdk = 21
        }

        compileOptions {
            sourceCompatibility(Constants.JAVA_VERSION)
            targetCompatibility(Constants.JAVA_VERSION)
        }
    }

    configureKotlin()
}

private fun Project.configureKotlin() {
    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    tasks.withType<KotlinCompilationTask<*>> {
        compilerOptions {
            optIn.addAll(
                listOf(
                    "kotlin.RequiresOptIn",
                    "com.mirego.pilot.components.InternalPilotComponentsApi",
                ),
            )
        }
    }
}
