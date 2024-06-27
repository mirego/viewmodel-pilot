package com.mirego.pilot.viewmodel.viewmodel.home

import com.mirego.pilot.components.PilotButton
import com.mirego.pilot.viewmodel.PilotViewModel
import com.mirego.pilot.viewmodel.viewmodel.navigation.NavigationManager

public abstract class HomeViewModel : PilotViewModel() {
    public abstract val navigationManager: NavigationManager

    public abstract val richTextPage: PilotButton<String>

    public abstract val textFieldPage: PilotButton<String>

    public abstract val otherComponentsPage: PilotButton<String>
}