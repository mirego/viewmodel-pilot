package com.mirego.pilot.components

import com.mirego.pilot.components.accessibility.PilotAccessibilityInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

public open class PilotButton<T : Any>(
    public open val isEnabled: StateFlow<Boolean> = MutableStateFlow(true),
    public open val content: StateFlow<T>,
    public open val accessibilityInfo: StateFlow<PilotAccessibilityInfo?> = MutableStateFlow(null),
    public open val action: () -> Unit,
)
