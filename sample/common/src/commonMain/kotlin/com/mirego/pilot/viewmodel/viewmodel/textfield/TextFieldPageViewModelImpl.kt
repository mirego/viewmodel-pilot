package com.mirego.pilot.viewmodel.viewmodel.textfield

import com.mirego.pilot.components.PilotButton
import com.mirego.pilot.viewmodel.viewmodel.navigation.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow

public class TextFieldPageViewModelImpl(
    private val navigationManager: NavigationManager
) : TextFieldPageViewModel() {

    override val backButton: PilotButton<String> = PilotButton(
        content = MutableStateFlow("Back")
    ) {
        navigationManager.pop()
    }

    override val title: String = "TextField"
}
