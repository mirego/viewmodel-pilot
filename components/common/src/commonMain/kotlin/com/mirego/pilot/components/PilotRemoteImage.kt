package com.mirego.pilot.components

public data class PilotRemoteImage(
    val url: String?,
    val placeholder: PilotImageResource?,
    public val contentDescription: String? = null,
)
