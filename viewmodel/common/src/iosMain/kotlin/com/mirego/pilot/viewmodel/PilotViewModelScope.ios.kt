package com.mirego.pilot.viewmodel

import kotlinx.coroutines.CoroutineScope

public actual val com.mirego.pilot.viewmodel.PilotViewModel.viewModelScope: CoroutineScope
    get() = viewModelScope
