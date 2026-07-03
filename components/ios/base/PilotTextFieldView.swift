import PILOT_FRAMEWORK_NAME
import SwiftUI

public struct PilotTextFieldView<Label: View>: View {
    private let pilotTextField: PilotTextField
    private let placeholderBuilder: (String) -> Label

    @ObservedObject private var text: StateObservable<String>
    @ObservedObject private var placeholder: StateObservable<String>
    @ObservedObject private var isEnabled: StateObservable<KotlinBoolean>
    @ObservedObject private var keyboardType: StateObservable<PilotKeyboardType>
    @ObservedObject private var keyboardReturnKeyType: StateObservable<PilotKeyboardReturnKeyType>
    @ObservedObject private var contentType: StateObservable<PilotTextContentType>
    @ObservedObject private var textObfuscationMode: StateObservable<PilotTextObfuscationMode>
    @ObservedObject private var autoCorrect: StateObservable<KotlinBoolean>
    @ObservedObject private var autoCapitalization: StateObservable<PilotKeyboardAutoCapitalization>

    @State private var textFieldText: String

    public init(_ pilotTextField: PilotTextField, placeholderBuilder: @escaping (String) -> Label) {
        self.pilotTextField = pilotTextField
        self.placeholderBuilder = placeholderBuilder

        _text = ObservedObject(wrappedValue: StateObservable(pilotTextField.text))
        _placeholder = ObservedObject(wrappedValue: StateObservable(pilotTextField.placeholder))
        _isEnabled = ObservedObject(wrappedValue: StateObservable(pilotTextField.isEnabled))
        _keyboardType = ObservedObject(wrappedValue: StateObservable(pilotTextField.keyboardType))
        _keyboardReturnKeyType = ObservedObject(wrappedValue: StateObservable(pilotTextField.keyboardReturnKeyType))
        _contentType = ObservedObject(wrappedValue: StateObservable(pilotTextField.contentType))
        _textObfuscationMode = ObservedObject(wrappedValue: StateObservable(pilotTextField.textObfuscationMode))
        _autoCorrect = ObservedObject(wrappedValue: StateObservable(pilotTextField.autoCorrect))
        _autoCapitalization = ObservedObject(wrappedValue: StateObservable(pilotTextField.autoCapitalization))

        _textFieldText = State(initialValue: pilotTextField.formatText(pilotTextField.transformText(pilotTextField.text.value)))
    }

    public var body: some View {
        baseTextField
            .onSubmit {
                pilotTextField.onReturnKeyTap()
            }
            .submitLabel(keyboardReturnKeyType.value.submitLabel)
        #if canImport(UIKit)
            .keyboardType(keyboardType.value.uiKeyboardType)
            .autocapitalization(autoCapitalization.value.uiTextAutocapitalizationType)
            .textContentType(contentType.value.uiTextContentType)
        #endif
            .disableAutocorrection(!autoCorrect.value.boolValue)
            .disabled(!isEnabled.value.boolValue)
            .textFieldStyle(ExtendedTapAreaTextFieldStyle())
            .onChange(of: text.value) { newValue in
                textFieldText = pilotTextField.formatText(newValue)
            }
            .onChange(of: textFieldText) { newValue in
                let unformattedText = pilotTextField.unformatText(newValue)
                textFieldText = pilotTextField.formatText(pilotTextField.transformText(unformattedText))
                pilotTextField.onValueChange(text: unformattedText)
            }
    }

    private var baseTextField: some View {
        Group {
            if textObfuscationMode.value == .hidden {
                SecureField(text: $textFieldText) {
                    placeholderBuilder(placeholder.value)
                }
            } else {
                TextField(text: $textFieldText) {
                    placeholderBuilder(placeholder.value)
                }
            }
        }
        .transaction { transaction in
            transaction.animation = nil
        }
    }
}

struct ExtendedTapAreaTextFieldStyle: TextFieldStyle {
    @FocusState private var textFieldFocused: Bool

    func _body(configuration: TextField<Self._Label>) -> some View {
        configuration
            .padding(.vertical, 16)
            .focused($textFieldFocused)
            .contentShape(Rectangle())
            .onTapGesture {
                textFieldFocused = true
            }
            .padding(.vertical, -16)
    }
}
