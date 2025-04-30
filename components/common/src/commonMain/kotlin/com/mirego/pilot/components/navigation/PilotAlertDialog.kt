package com.mirego.pilot.components.navigation

import com.mirego.pilot.components.content.PilotLocalImageContent

public data class PilotAlertDialog(
    val icon: PilotLocalImageContent? = null,
    val title: String,
    val text: String? = null,
    val confirmButton: ConfirmButton,
    val dismissButton: DismissButton? = null,
) {
    public interface Button {
        public val title: String
        public val action: () -> Unit
    }

    public data class ConfirmButton(
        override val title: String,
        val isDestructive: Boolean = false,
        override val action: () -> Unit,
    ) : Button

    public data class DismissButton(
        override val title: String,
        override val action: () -> Unit,
    ) : Button
}
