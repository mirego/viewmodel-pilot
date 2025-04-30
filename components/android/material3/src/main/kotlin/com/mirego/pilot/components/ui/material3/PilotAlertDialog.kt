package com.mirego.pilot.components.ui.material3

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.DialogProperties
import com.mirego.pilot.components.content.PilotLocalImageContent
import com.mirego.pilot.components.navigation.PilotAlertDialog
import com.mirego.pilot.components.ui.pilotImageResourcePainter

@Composable
public fun PilotAlertDialog(
    alertDialog: PilotAlertDialog,
    modifier: Modifier = Modifier,
    icon: @Composable (PilotLocalImageContent) -> Unit = { DefaultIcon(it) },
    title: @Composable (String) -> Unit = { Text(text = it) },
    text: @Composable (String) -> Unit = { Text(text = it) },
    confirmButton: @Composable (PilotAlertDialog.Button, close: () -> Unit) -> Unit = { button, close -> DefaultButton(button, close) },
    dismissButton: @Composable (PilotAlertDialog.Button, close: () -> Unit) -> Unit = { button, close -> DefaultButton(button, close) },
    shape: Shape = AlertDialogDefaults.shape,
    containerColor: Color = AlertDialogDefaults.containerColor,
    iconContentColor: Color = AlertDialogDefaults.iconContentColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    textContentColor: Color = AlertDialogDefaults.textContentColor,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
    properties: DialogProperties = DialogProperties(),
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        icon = alertDialog.icon?.let { { icon(it) } },
        title = { title(alertDialog.title) },
        text = alertDialog.text?.let { { text(it) } },
        onDismissRequest = onDismissRequest,
        confirmButton = { confirmButton(alertDialog.confirmButton, onDismissRequest) },
        dismissButton = alertDialog.dismissButton?.let { { dismissButton(it, onDismissRequest) } },
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        iconContentColor = iconContentColor,
        titleContentColor = titleContentColor,
        textContentColor = textContentColor,
        tonalElevation = tonalElevation,
        properties = properties,
    )
}

@Composable
private fun DefaultIcon(content: PilotLocalImageContent) {
    Icon(
        painter = pilotImageResourcePainter(content.imageResource),
        contentDescription = content.contentDescription,
    )
}

@Composable
private fun DefaultButton(button: PilotAlertDialog.Button, close: () -> Unit) {
    TextButton(
        onClick = {
            close()
            button.action()
        },
    ) {
        Text(text = button.title)
    }
}
