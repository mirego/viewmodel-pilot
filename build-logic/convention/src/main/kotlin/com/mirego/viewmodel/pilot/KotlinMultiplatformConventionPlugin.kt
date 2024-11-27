package com.mirego.viewmodel.pilot

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jlleitschuh.gradle.ktlint")
                apply("org.jetbrains.kotlinx.binary-compatibility-validator")
                apply("mirego.publish")
            }

            configureApiValidationPlugin()

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
            }

            extensions.configure<KotlinMultiplatformExtension> {
                jvmToolchain(17)

                applyDefaultHierarchyTemplate()
                explicitApi()
                @OptIn(ExperimentalKotlinGradlePluginApi::class)
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }

                iosX64()
                iosArm64()
                iosSimulatorArm64()
                macosArm64()
                macosX64()

                androidTarget {
                    publishLibraryVariants("release", "debug")
                }
            }
        }
    }
}
