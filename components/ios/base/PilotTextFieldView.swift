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
    @ObservedObject private var textObfuscationMode: StateObservable<PilotTextObfuscationMode>
    @ObservedObject private var autoCorrect: StateObservable<KotlinBoolean>
    @ObservedObject private var autoCapitalization: StateObservable<PilotKeyboardAutoCapitalization>

    @State private var textFieldText: String
    @FocusState private var focusedField: FieldType?

    private enum FieldType {
        case secure
        case plain
    }

    public init(_ pilotTextField: PilotTextField, placeholderBuilder: @escaping (String) -> Label) {
        self.pilotTextField = pilotTextField
        self.placeholderBuilder = placeholderBuilder

        _text = ObservedObject(wrappedValue: StateObservable(pilotTextField.text))
        _placeholder = ObservedObject(wrappedValue: StateObservable(pilotTextField.placeholder))
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
            .textFieldStyle(ExtendedTapAreaTextFieldStyle())
            .onChange(of: text.value) { newValue in
                textFieldText = pilotTextField.formatText(newValue)
            }
            .onChange(of: textFieldText) { newValue in
                let unformattedText = pilotTextField.unformatText(newValue)
                textFieldText = pilotTextField.formatText(pilotTextField.transformText(unformattedText))
                pilotTextField.onValueChange(text: unformattedText)
            }
            .onChange(of: textObfuscationMode.value) { newValue in
                if focusedField != nil {
                    withAnimation(nil) {
                        focusedField = newValue == .hidden ? .secure : .plain
                    }
                }
            }
    }

    @ViewBuilder
    private var baseTextField: some View {
        ZStack {
            SecureField(text: $textFieldText) {
                placeholderBuilder(placeholder.value)
            }
            .opacity(textObfuscationMode.value == .hidden ? 1 : 0)
            .focused($focusedField, equals: .secure)

            TextField(text: $textFieldText) {
                placeholderBuilder(placeholder.value)
            }
            .opacity(textObfuscationMode.value == .visible ? 1 : 0)
            .focused($focusedField, equals: .plain)
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
