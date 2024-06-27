package com.mirego.pilot.viewmodel.viewmodel.other

import com.mirego.pilot.components.PilotButton
import com.mirego.pilot.viewmodel.viewmodel.navigation.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow

public class OtherComponentsPageViewModelImpl(
    private val navigationManager: NavigationManager
) : OtherComponentsPageViewModel() {

    override val backButton: PilotButton<String> = PilotButton(
        content = MutableStateFlow("Back")
    ) {
        navigationManager.pop()
    }

    override val title: String = "Others"
}
