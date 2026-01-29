package com.mirego.pilot.components

import com.mirego.pilot.components.type.PilotKeyboardAutoCapitalization
import com.mirego.pilot.components.type.PilotKeyboardReturnKeyType
import com.mirego.pilot.components.type.PilotKeyboardType
import com.mirego.pilot.components.type.PilotTextContentType
import com.mirego.pilot.components.type.PilotTextObfuscationMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

public open class PilotTextField(
    private val textFlow: MutableStateFlow<String>,
    public val placeholder: StateFlow<String>,
    public val keyboardType: StateFlow<PilotKeyboardType> = MutableStateFlow(PilotKeyboardType.Default),
    public val keyboardReturnKeyType: StateFlow<PilotKeyboardReturnKeyType> = MutableStateFlow(PilotKeyboardReturnKeyType.Default),
    public val contentType: StateFlow<PilotTextContentType> = MutableStateFlow(PilotTextContentType.NotSet),
    public val textObfuscationMode: StateFlow<PilotTextObfuscationMode> = MutableStateFlow(PilotTextObfuscationMode.Visible),
    public val autoCorrect: StateFlow<Boolean> = MutableStateFlow(true),
    public val autoCapitalization: StateFlow<PilotKeyboardAutoCapitalization> = MutableStateFlow(PilotKeyboardAutoCapitalization.Sentences),
    public val onReturnKeyTap: () -> Unit = {},
    public val formatText: (text: String) -> String = { it },
    public val unformatText: (text: String) -> String = { it },
    public val transformText: (text: String) -> String = { it },
) {

    public val text: StateFlow<String> = textFlow

    public fun onValueChange(text: String) {
        textFlow.value = transformText(text)
    }
}
