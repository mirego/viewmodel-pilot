package com.mirego.pilot.components

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

public open class PilotSwitch<T : Any>(
    public open val isOn: StateFlow<Boolean>,
    public open val label: T,
    public open val isEnabled: StateFlow<Boolean> = MutableStateFlow(true),
    public open val onCheckedChange: (Boolean) -> Unit = {},
)
