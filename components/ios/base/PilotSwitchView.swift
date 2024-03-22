import Shared
import SwiftUI

public struct PilotSwitchView<Content, Label>: View where Content: AnyObject, Label: View {
    private let pilotSwitch: PilotSwitch<Content>
    private let labelBuilder: (Content) -> Label

    @ObservedObject private var isOn: StateObservable<KotlinBoolean>

    public init(_ pilotSwitch: PilotSwitch<Content>, labelBuilder: @escaping (Content) -> Label) {
        self.pilotSwitch = pilotSwitch
        self.labelBuilder = labelBuilder
        _isOn = ObservedObject(wrappedValue: StateObservable(pilotSwitch.isOn))
    }

    public var body: some View {
        Toggle(
            isOn: Binding(
                get: {
                    isOn.value.boolValue
                }, set: {
                    pilotSwitch.onCheckedChange(KotlinBoolean(value: $0))
                }
            )
        ) {
            labelBuilder(pilotSwitch.label)
        }
    }
}
