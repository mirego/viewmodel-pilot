package com.mirego.pilot.components.ui.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.testTag
import com.mirego.pilot.components.accessibility.PilotAccessibilityInfo

public fun Modifier.clearAndSetAccessibility(accessibilityInfo: PilotAccessibilityInfo?): Modifier =
    accessibilityInfo?.let {
        clearAndSetSemantics {
            accessibility(it)
        }
    } ?: this

private fun SemanticsPropertyReceiver.accessibility(accessibilityInfo: PilotAccessibilityInfo) {
    accessibilityInfo.label?.let {
        contentDescription = it
    }
    accessibilityInfo.identifier?.let {
        testTag = it
    }
    if (accessibilityInfo.hint != null || accessibilityInfo.action != null) {
        onClick(label = accessibilityInfo.hint, action = accessibilityInfo.action)
    }
    customActions = accessibilityInfo.customActions.map { accessibilityAction ->
        CustomAccessibilityAction(
            label = accessibilityAction.label,
            action = accessibilityAction.action,
        )
    }
}
