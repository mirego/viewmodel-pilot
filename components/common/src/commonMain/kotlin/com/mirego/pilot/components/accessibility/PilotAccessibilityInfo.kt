package com.mirego.pilot.components.accessibility

public data class PilotAccessibilityInfo(
    public val label: String? = null,
    public val identifier: String? = null,
    public val hint: String? = null,
    public val action: (() -> Boolean)? = null,
    public val customActions: List<PilotAccessibilityAction> = emptyList(),
)
