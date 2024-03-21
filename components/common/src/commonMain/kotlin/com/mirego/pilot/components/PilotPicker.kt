package com.mirego.pilot.components

import kotlinx.coroutines.flow.StateFlow

public class PilotPicker<LABEL : Any, ITEM : Any>(
    public val label: StateFlow<LABEL>,
    public val items: StateFlow<List<ITEM>>,
    public val onSelectedIndex: (Int) -> Unit,
)
