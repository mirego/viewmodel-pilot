package com.mirego.pilot.components.lifecycle

import com.mirego.pilot.viewmodel.PilotViewModel

public open class PilotAppearanceLifecycleViewModel : PilotAppearanceLifecycle by PilotAppearanceLifecycleImpl(), PilotViewModel() {
    override fun onCleared() {
        super.onCleared()
        onDisappear()
    }
}
