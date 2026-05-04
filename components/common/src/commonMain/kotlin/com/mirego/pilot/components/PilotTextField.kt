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
    public open val placeholder: StateFlow<String>,
    public open val isEnabled: StateFlow<Boolean> = MutableStateFlow(true),
    public open val keyboardType: StateFlow<PilotKeyboardType> = MutableStateFlow(PilotKeyboardType.Default),
    public open val keyboardReturnKeyType: StateFlow<PilotKeyboardReturnKeyType> = MutableStateFlow(PilotKeyboardReturnKeyType.Default),
    public open val contentType: StateFlow<PilotTextContentType> = MutableStateFlow(PilotTextContentType.NotSet),
    public open val textObfuscationMode: StateFlow<PilotTextObfuscationMode> = MutableStateFlow(PilotTextObfuscationMode.Visible),
    public open val autoCorrect: StateFlow<Boolean> = MutableStateFlow(true),
    public open val autoCapitalization: StateFlow<PilotKeyboardAutoCapitalization> = MutableStateFlow(PilotKeyboardAutoCapitalization.Sentences),
    public open val onReturnKeyTap: () -> Unit = {},
    public open val formatText: (text: String) -> String = { it },
    public open val unformatText: (text: String) -> String = { it },
    public open val transformText: (text: String) -> String = { it },
) {

    public val text: StateFlow<String> = textFlow

    public fun onValueChange(text: String) {
        textFlow.value = transformText(text)
    }
}
