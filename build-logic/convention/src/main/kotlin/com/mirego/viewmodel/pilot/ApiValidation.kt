package com.mirego.viewmodel.pilot

import kotlinx.validation.ApiValidationExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureApiValidationPlugin() {
    extensions.configure<ApiValidationExtension> {
        nonPublicMarkers += listOf(
            "com.mirego.pilot.components.InternalPilotComponentsApi"
        )
    }
}