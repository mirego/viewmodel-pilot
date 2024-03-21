package com.mirego.pilot.components

public data class PilotResizableRemoteImage(
    private val urlResolver: (width: Int?, height: Int?) -> String?,
    val placeholder: PilotImageResource? = null,
    public val contentDescription: String? = null,
) {
    public fun url(width: Int? = null, height: Int? = null): String? {
        return urlResolver(width, height)
    }
}
