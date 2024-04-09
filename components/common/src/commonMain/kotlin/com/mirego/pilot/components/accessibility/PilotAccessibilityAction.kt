package com.mirego.pilot.components.accessibility

public data class PilotAccessibilityAction(
    public val label: String,
    public val action: () -> Boolean,
)
