package com.mirego.viewmodel.pilot

import com.android.build.gradle.LibraryExtension
import com.mirego.viewmodel.pilot.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jlleitschuh.gradle.ktlint")
                apply("org.jetbrains.kotlinx.binary-compatibility-validator")
                apply("mirego.publish")
                if (!plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")) {
                    apply("org.jetbrains.kotlin.android")

                    extensions.configure<KotlinAndroidProjectExtension> {
                        jvmToolchain(17)
                        explicitApi()
                    }
                }
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
            }
        }
    }
}
