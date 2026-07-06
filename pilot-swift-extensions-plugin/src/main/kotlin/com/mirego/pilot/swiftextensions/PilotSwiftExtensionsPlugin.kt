package com.mirego.pilot.swiftextensions

import org.gradle.api.Plugin
import org.gradle.api.Project

class PilotSwiftExtensionsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create(
            "pilotSwiftExtensions",
            PilotSwiftExtensionsExtension::class.java,
        )

        project.tasks.register("syncPilotSwiftExtensions", SyncSwiftExtensionsTask::class.java) {
            frameworkName.set(extension.frameworkName)
            modules.set(extension.modules)
            outputDir.set(extension.outputDir)
        }
    }
}
