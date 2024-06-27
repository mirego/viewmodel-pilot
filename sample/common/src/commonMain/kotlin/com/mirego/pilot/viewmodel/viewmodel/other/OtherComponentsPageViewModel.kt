package com.mirego.pilot.viewmodel.viewmodel.other

import com.mirego.pilot.components.PilotButton
import com.mirego.pilot.viewmodel.PilotViewModel

public abstract class OtherComponentsPageViewModel : PilotViewModel() {
    public abstract val backButton: PilotButton<String>

    public abstract val title: String
}
