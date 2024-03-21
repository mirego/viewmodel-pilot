package com.mirego.pilot.components.ui.type

import androidx.compose.ui.text.input.KeyboardType
import com.mirego.pilot.components.InternalPilotComponentsApi
import com.mirego.pilot.components.type.PilotKeyboardType

@InternalPilotComponentsApi
public val PilotKeyboardType.composeValue: KeyboardType
    get() = when (this) {
        PilotKeyboardType.Default -> KeyboardType.Text
        PilotKeyboardType.Ascii -> KeyboardType.Ascii
        PilotKeyboardType.Number -> KeyboardType.Number
        PilotKeyboardType.Email -> KeyboardType.Email
        PilotKeyboardType.Password -> KeyboardType.Password
        PilotKeyboardType.NumberPassword -> KeyboardType.NumberPassword
        PilotKeyboardType.Phone -> KeyboardType.Phone
        PilotKeyboardType.URL -> KeyboardType.Uri
    }
