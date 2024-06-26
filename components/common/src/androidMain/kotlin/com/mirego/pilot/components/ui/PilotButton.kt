package com.mirego.pilot.components.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.mirego.pilot.components.PilotButton
import com.mirego.pilot.components.ui.extensions.clearAndSetAccessibility

@Composable
public fun <C : Any> PilotButton(pilotButton: PilotButton<C>, modifier: Modifier = Modifier, content: @Composable BoxScope.(field: C) -> Unit) {
    val isEnabled by pilotButton.isEnabled.collectAsState()
    val accessibilityInfo by pilotButton.accessibilityInfo.collectAsState()
    Box(
        modifier = modifier
            .clickable(enabled = isEnabled) { pilotButton.action() }
            .semantics {
                role = Role.Button
            }
            .clearAndSetAccessibility(accessibilityInfo),
    ) {
        val buttonContent by pilotButton.content.collectAsState()
        content(buttonContent)
    }
}
