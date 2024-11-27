package com.mirego.pilot.viewmodel

import kotlinx.coroutines.CoroutineScope

public actual val PilotViewModel.viewModelScope: CoroutineScope
    get() = viewModelScope
