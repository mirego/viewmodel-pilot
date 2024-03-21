package com.mirego.pilot.components.ui.type

import androidx.compose.ui.text.input.KeyboardCapitalization
import com.mirego.pilot.components.InternalPilotComponentsApi
import com.mirego.pilot.components.type.PilotKeyboardAutoCapitalization

@InternalPilotComponentsApi
public val PilotKeyboardAutoCapitalization.composeValue: KeyboardCapitalization
    get() = when (this) {
        PilotKeyboardAutoCapitalization.None -> KeyboardCapitalization.None
        PilotKeyboardAutoCapitalization.Sentences -> KeyboardCapitalization.Sentences
        PilotKeyboardAutoCapitalization.Words -> KeyboardCapitalization.Words
        PilotKeyboardAutoCapitalization.Characters -> KeyboardCapitalization.Characters
    }
