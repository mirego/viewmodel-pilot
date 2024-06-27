package com.mirego.pilot.viewmodel.viewmodel.home

import com.mirego.pilot.components.PilotButton
import com.mirego.pilot.viewmodel.viewModelScope
import com.mirego.pilot.viewmodel.viewmodel.navigation.NavigationManager
import com.mirego.pilot.viewmodel.viewmodel.navigation.NavigationRoute
import kotlinx.coroutines.flow.MutableStateFlow

public class HomeViewModelImpl : HomeViewModel() {
    override val navigationManager: NavigationManager = NavigationManager(viewModelScope)

    override val richTextPage: PilotButton<String> = PilotButton(
        content = MutableStateFlow("Rich Text")
    ) {
        navigationManager.push(NavigationRoute.RichTextPage)
    }

    override val textFieldPage: PilotButton<String> = PilotButton(
        content = MutableStateFlow("TextField")
    ) {
        navigationManager.push(NavigationRoute.TextFieldPage)
    }

    override val otherComponentsPage: PilotButton<String> = PilotButton(
        content = MutableStateFlow("Others")
    ) {
        navigationManager.push(NavigationRoute.OtherComponentsPage)
    }
}