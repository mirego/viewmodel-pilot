package com.mirego.viewmodel.pilot

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework

interface KotlinMultiplatformConventionPluginExtension {
    val configureFramework: Property<Framework.() -> Unit>
    val androidPublishLibraryVariants: Property<Array<String>>
}

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create<KotlinMultiplatformConventionPluginExtension>("kotlinMultiplatformConventionPluginExtension")
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
            }

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

                iosX64 {
                    binaries {
                        framework {
                            afterEvaluate {
                                extension.configureFramework.getOrNull()?.invoke(this@framework)
                            }
                        }
                    }
                }

                iosArm64 {
                    binaries {
                        framework {
                            afterEvaluate {
                                extension.configureFramework.getOrNull()?.invoke(this@framework)
                            }
                        }
                    }
                }

                iosSimulatorArm64 {
                    binaries {
                        framework {
                            afterEvaluate {
                                extension.configureFramework.getOrNull()?.invoke(this@framework)
                            }
                        }
                    }
                }

                androidTarget {
                    afterEvaluate {
                        publishLibraryVariants(*(extension.androidPublishLibraryVariants.getOrElse(arrayOf("release", "debug"))))
                    }
                }
            }
        }
    }
}
