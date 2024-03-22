import Shared
import SwiftUI

public struct PilotTextFieldView<Label>: View where Label: View {
    private let pilotTextField: PilotTextField
    private let placeholderBuilder: (String) -> Label

    @ObservedObject private var text: StateObservable<String>
    @ObservedObject private var placeholder: StateObservable<String>
    @ObservedObject private var keyboardType: StateObservable<PilotKeyboardType>
    @ObservedObject private var keyboardReturnKeyType: StateObservable<PilotKeyboardReturnKeyType>
    @ObservedObject private var contentType: StateObservable<PilotTextContentType>
    @ObservedObject private var autoCorrect: StateObservable<KotlinBoolean>
    @ObservedObject private var autoCapitalization: StateObservable<PilotKeyboardAutoCapitalization>

    public init(_ pilotTextField: PilotTextField, placeholderBuilder: @escaping (String) -> Label) {
        self.pilotTextField = pilotTextField
        self.placeholderBuilder = placeholderBuilder

        _text = ObservedObject(wrappedValue: StateObservable(pilotTextField.text))
        _placeholder = ObservedObject(wrappedValue: StateObservable(pilotTextField.placeholder))
        _keyboardType = ObservedObject(wrappedValue: StateObservable(pilotTextField.keyboardType))
        _keyboardReturnKeyType = ObservedObject(wrappedValue: StateObservable(pilotTextField.keyboardReturnKeyType))
        _contentType = ObservedObject(wrappedValue: StateObservable(pilotTextField.contentType))
        _autoCorrect = ObservedObject(wrappedValue: StateObservable(pilotTextField.autoCorrect))
        _autoCapitalization = ObservedObject(wrappedValue: StateObservable(pilotTextField.autoCapitalization))
    }

    private var textBinding: Binding<String> {
        Binding {
            pilotTextField.formatText(pilotTextField.transformText(text.value))
        } set: { newValue in
            let unformattedText = pilotTextField.unformatText(newValue)
            pilotTextField.onValueChange(text: unformattedText)
        }
    }

    public var body: some View {
        TextField(text: textBinding) {
            placeholderBuilder(placeholder.value)
        }
        .onSubmit {
            pilotTextField.onReturnKeyTap()
        }
        .submitLabel(keyboardReturnKeyType.value.submitLabel)
        .keyboardType(keyboardType.value.uiKeyboardType)
        .textContentType(contentType.value.uiTextContentType)
        .disableAutocorrection(!autoCorrect.value.boolValue)
        .autocapitalization(autoCapitalization.value.uiTextAutocapitalizationType)
        .textFieldStyle(ExtendedTapAreaTextFieldStyle())
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
