package com.mirego.pilot.components.ui.material3

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import com.mirego.pilot.components.ui.PilotFormattedVisualTransformation
import com.mirego.pilot.components.PilotTextField
import com.mirego.pilot.components.ui.mergeWith
import com.mirego.pilot.components.ui.type.composeValue

@Composable
public fun PilotTextField(
    pilotTextField: PilotTextField,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    placeHolderStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors(),
) {
    val textValue by pilotTextField.text.collectAsState()
    val placeHolder by pilotTextField.placeholder.collectAsState()
    val autoCapitalization by pilotTextField.autoCapitalization.collectAsState()
    val autoCorrect by pilotTextField.autoCorrect.collectAsState()
    val keyboardType by pilotTextField.keyboardType.collectAsState()
    val keyboardReturnKeyType by pilotTextField.keyboardReturnKeyType.collectAsState()

    OutlinedTextField(
        value = textValue,
        onValueChange = pilotTextField::onValueChange,
        placeholder = {
            Text(
                text = placeHolder,
                style = placeHolderStyle,
            )
        },
        modifier = modifier,
        textStyle = textStyle,
        label = label,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        visualTransformation = PilotFormattedVisualTransformation(pilotTextField.formatText),
        isError = isError,
        keyboardActions = pilotTextField.mergeWith(keyboardActions),
        keyboardOptions = KeyboardOptions(
            capitalization = autoCapitalization.composeValue,
            autoCorrect = autoCorrect,
            keyboardType = keyboardType.composeValue,
            imeAction = keyboardReturnKeyType.composeValue,
        ),
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
    )
}
