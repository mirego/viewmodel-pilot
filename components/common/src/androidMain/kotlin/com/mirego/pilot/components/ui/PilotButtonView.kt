package com.mirego.pilot.components.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.mirego.pilot.components.PilotButton

@Composable
public fun <C : Any> PilotButton(pilotButton: PilotButton<C>, modifier: Modifier = Modifier, content: @Composable (field: C) -> Unit) {
    val isEnabled by pilotButton.isEnabled.collectAsState()
    Box(
        modifier = modifier
            .clickable(enabled = isEnabled) { pilotButton.action() }
            .semantics {
                role = Role.Button
            },
    ) {
        val buttonContent by pilotButton.content.collectAsState()
        content(buttonContent)
    }
}
