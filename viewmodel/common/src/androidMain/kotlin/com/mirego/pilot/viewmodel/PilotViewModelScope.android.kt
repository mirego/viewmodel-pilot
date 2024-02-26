package com.mirego.pilot.viewmodel

import kotlinx.coroutines.CoroutineScope
import androidx.lifecycle.viewModelScope as androidxViewModelScope

public actual val PilotViewModel.viewModelScope: CoroutineScope
    get() = androidxViewModelScope
