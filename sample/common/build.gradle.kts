import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework

plugins {
    id("buildlogic.kotlin.multiplatform")
    id("org.jetbrains.kotlin.native.cocoapods")
    alias(libs.plugins.skie)
}

val trikot_framework_name = "Shared"

fun configureFramework(framework: Framework) {
    with(framework) {
        baseName = trikot_framework_name

        export(projects.navigation)
        export(projects.components)
        export(projects.viewmodel)
    }
}

skie {
    analytics {
        disableUpload.set(true)
    }
}

kotlin {
    cocoapods {
        name = trikot_framework_name
        summary = "News Core Multiplatform Library"
        homepage = "www.mirego.com"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../ios/Podfile")
        extraSpecAttributes = mutableMapOf(
            "prepare_command" to """
                <<-CMD
                    ../../gradlew :sample:generateDummyFramework
                CMD
            """.trimIndent(),
        )
        framework {
            configureFramework(this)
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }

        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                api(projects.navigation)
                api(projects.viewmodel)
                api(projects.components)
            }
        }

        androidMain.dependencies {
        }

        iosMain.dependencies {
        }
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

android {
    namespace = "com.mirego.pilot.viewmodel"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
}

dependencies {
    // add("kspCommonMainMetadata", libs.koin.ksp.compiler)
}

// tasks.named("runKtlintCheckOverCommonMainSourceSet").dependsOn("kspCommonMainKotlinMetadata")
// tasks.named("runKtlintFormatOverCommonMainSourceSet").dependsOn("kspCommonMainKotlinMetadata")

/*
tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().all {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    } else {
        // dependsOn(tasks.withType<com.mirego.kword.KWordEnumGenerate>())
    }
}
*/
ktlint {
    filter {
        exclude { element -> element.file.path.contains("generated/") }
    }
}
