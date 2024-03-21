package com.mirego.pilot.components

import kotlinx.coroutines.flow.StateFlow

public class PilotSwitch<T : Any>(
    public val isOn: StateFlow<Boolean>,
    public val label: T,
    public val onCheckedChange: (Boolean) -> Unit = {},
)
