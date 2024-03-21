package com.mirego.pilot.components.ui.type

import androidx.compose.ui.text.input.ImeAction
import com.mirego.pilot.components.InternalPilotComponentsApi
import com.mirego.pilot.components.type.PilotKeyboardReturnKeyType

@InternalPilotComponentsApi
public val PilotKeyboardReturnKeyType.composeValue: ImeAction
    get() = when (this) {
        PilotKeyboardReturnKeyType.Default -> ImeAction.Default
        PilotKeyboardReturnKeyType.Done -> ImeAction.Done
        PilotKeyboardReturnKeyType.Go -> ImeAction.Go
        PilotKeyboardReturnKeyType.Next -> ImeAction.Next
        PilotKeyboardReturnKeyType.Search -> ImeAction.Search
        PilotKeyboardReturnKeyType.Send -> ImeAction.Send
    }
