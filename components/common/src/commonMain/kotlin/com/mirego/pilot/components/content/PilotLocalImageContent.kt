package com.mirego.pilot.components.content

import com.mirego.pilot.components.PilotImageResource

public open class PilotLocalImageContent(
    public val imageResource: PilotImageResource,
    public val contentDescription: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as PilotLocalImageContent

        if (imageResource != other.imageResource) return false
        if (contentDescription != other.contentDescription) return false

        return true
    }

    override fun hashCode(): Int {
        var result = imageResource.hashCode()
        result = 31 * result + (contentDescription?.hashCode() ?: 0)
        return result
    }
}
