package com.mirego.pilot.components

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

public open class PilotPicker<LABEL : Any, ITEM : Any>(
    public open val label: StateFlow<LABEL>,
    public open val items: StateFlow<List<ITEM>>,
    public open val isEnabled: StateFlow<Boolean> = MutableStateFlow(true),
    public open val onSelectedIndex: (Int) -> Unit,
)
