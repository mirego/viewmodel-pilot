package com.mirego.pilot.components.ui

import androidx.compose.foundation.text.KeyboardActions
import com.mirego.pilot.components.InternalPilotComponentsApi
import com.mirego.pilot.components.PilotTextField

@InternalPilotComponentsApi
public fun PilotTextField.mergeWith(keyboardActions: KeyboardActions): KeyboardActions =
    KeyboardActions(
        onDone = {
            onReturnKeyTap.invoke()
            keyboardActions.onDone?.invoke(this)
        },
        onNext = {
            onReturnKeyTap.invoke()
            keyboardActions.onNext?.invoke(this)
        },
        onGo = {
            onReturnKeyTap.invoke()
            keyboardActions.onGo?.invoke(this)
        },
        onPrevious = {
            onReturnKeyTap.invoke()
            keyboardActions.onPrevious?.invoke(this)
        },
        onSearch = {
            onReturnKeyTap.invoke()
            keyboardActions.onSearch?.invoke(this)
        },
        onSend = {
            onReturnKeyTap.invoke()
            keyboardActions.onSend?.invoke(this)
        },
    )
