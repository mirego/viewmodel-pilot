package com.mirego.pilot.components

@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This is internal API for Pilot Components. Do not use it directly.",
)
@Retention(AnnotationRetention.BINARY)
public annotation class InternalPilotComponentsApi
