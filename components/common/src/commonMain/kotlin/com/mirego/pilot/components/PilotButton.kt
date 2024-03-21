package com.mirego.pilot.components

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

public open class PilotButton<T : Any>(
    public open val isEnabled: StateFlow<Boolean> = MutableStateFlow(true),
    public open val content: StateFlow<T>,
    public open val action: () -> Unit,
)
