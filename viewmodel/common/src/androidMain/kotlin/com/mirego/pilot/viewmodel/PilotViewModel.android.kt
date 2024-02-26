package com.mirego.pilot.viewmodel

import androidx.lifecycle.ViewModel as AndroidXViewModel

public actual abstract class PilotViewModel actual constructor() : AndroidXViewModel() {
    actual override fun onCleared() {
        super.onCleared()
    }
}
