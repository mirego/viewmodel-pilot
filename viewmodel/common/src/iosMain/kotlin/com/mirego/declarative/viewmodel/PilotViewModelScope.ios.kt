package com.mirego.declarative.viewmodel

import kotlinx.coroutines.CoroutineScope

public actual val PilotViewModel.viewModelScope: CoroutineScope
    get() = viewModelScope
