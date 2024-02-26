package com.mirego.declarative.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

public actual abstract class PilotViewModel actual constructor() {

    internal val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    protected actual open fun onCleared() {
        viewModelScope.cancel()
    }

    public fun clear() {
        onCleared()
    }
}
