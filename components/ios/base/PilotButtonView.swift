import Shared
import SwiftUI

public struct PilotButtonView<Content, Label>: View where Content: AnyObject, Label: View {
    private let button: PilotButton<Content>
    private let labelBuilder: (Content) -> Label

    var externalActionHandler: ((_ action: () -> Void) -> Void)?

    @ObservedObject private var content: StateObservable<Content>
    @ObservedObject private var isEnabled: StateObservable<KotlinBoolean>

    public init(_ button: PilotButton<Content>, labelBuilder: @escaping (Content) -> Label, animation: ((Content, Content) -> Animation?)? = nil) {
        self.button = button
        self.labelBuilder = labelBuilder

        _content = ObservedObject(wrappedValue: StateObservable(button.content, animation: animation))
        _isEnabled = ObservedObject(wrappedValue: StateObservable(button.isEnabled))
    }

    public var body: some View {
        Button {
            if let externalActionHandler = externalActionHandler {
                externalActionHandler {
                    button.action()
                }
            } else {
                button.action()
            }
        } label: {
            labelBuilder(content.value)
        }
        .disabled(!isEnabled.value.boolValue)
    }
}

extension PilotButtonView {
    public func externalActionHandler(_ action: @escaping (_ action: () -> Void) -> Void) -> PilotButtonView {
        var button = self
        button.externalActionHandler = action
        return button
    }
}
