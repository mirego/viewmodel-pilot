package com.mirego.pilot.components.type

/**
 * Defines how text should be obscured in a secure text field.
 */
public enum class PilotTextObfuscationMode {
    /**
     * All characters are obscured (replaced with bullets/dots).
     */
    Hidden,

    /**
     * All characters are visible as plain text.
     */
    Visible,
}
