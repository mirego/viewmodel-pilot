package com.mirego.pilot.viewmodel.viewmodel.richtext

import com.mirego.pilot.components.PilotButton
import com.mirego.pilot.viewmodel.viewmodel.navigation.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow

public class RichTextPageViewModelImpl(
    private val navigationManager: NavigationManager
) : RichTextPageViewModel() {

    override val backButton: PilotButton<String> = PilotButton(
        content = MutableStateFlow("Back")
    ) {
        navigationManager.pop()
    }

    override val title: String = "Rich Text"
}
