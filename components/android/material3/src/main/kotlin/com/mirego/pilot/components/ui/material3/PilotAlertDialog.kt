package com.mirego.pilot.components.ui.material3

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.DialogProperties
import com.mirego.pilot.components.navigation.PilotAlertDialog

@Composable
public fun PilotAlertDialog(
    alertDialog: PilotAlertDialog,
    modifier: Modifier = Modifier,
    shape: Shape = AlertDialogDefaults.shape,
    containerColor: Color = AlertDialogDefaults.containerColor,
    iconContentColor: Color = AlertDialogDefaults.iconContentColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    textContentColor: Color = AlertDialogDefaults.textContentColor,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
    properties: DialogProperties = DialogProperties(),
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        icon = {

        },
        title = {
            Text(text = alertDialog.title)
        },
        text = alertDialog.text?.let { { Text(text = it) } },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            PilotAlertDialogButton(alertDialog.confirmButton)
        },
        dismissButton = alertDialog.dismissButton?.let { { PilotAlertDialogButton(it) } },
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        iconContentColor = iconContentColor,
        titleContentColor = titleContentColor,
        textContentColor = textContentColor,
        tonalElevation = tonalElevation,
        properties = properties
    )
}

@Composable
private fun PilotAlertDialogButton(
    button: PilotAlertDialog.Button
) {
    TextButton(
        onClick = {
            button.action()
        }
    ) {
        Text(text = button.title)
    }
}
