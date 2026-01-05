import Shared
import SwiftUI

extension View {
    public func pilotAccessibility(
        _ info: PilotAccessibilityInfo?,
        traits: AccessibilityTraits = [],
        childBehavior: AccessibilityChildBehavior = .ignore
    ) -> some View {
        modifier(NullableAccessibilityViewModifier(info: info, traits: traits, childBehavior: childBehavior))
    }

    public func pilotAccessibility(
        _ infoFlow: SkieSwiftOptionalStateFlow<PilotAccessibilityInfo>,
        traits: AccessibilityTraits = [],
        childBehavior: AccessibilityChildBehavior = .ignore
    ) -> some View {
        modifier(NullableAccessibilityViewModifier(infoFlow: infoFlow, traits: traits, childBehavior: childBehavior))
    }

    public func pilotAccessibility(
        _ infoFlow: SkieSwiftStateFlow<PilotAccessibilityInfo>,
        traits: AccessibilityTraits = [],
        childBehavior: AccessibilityChildBehavior = .ignore
    ) -> some View {
        modifier(AccessibilityViewModifier(infoFlow: infoFlow, traits: traits, childBehavior: childBehavior))
    }

    public func pilotAccessibility(
        _ infoFlow: SkieSwiftOptionalFlow<PilotAccessibilityInfo>,
        initialValue: PilotAccessibilityInfo? = nil,
        traits: AccessibilityTraits = [],
        childBehavior: AccessibilityChildBehavior = .ignore
    ) -> some View {
        modifier(NullableAccessibilityViewModifier(infoFlow: infoFlow, initialValue: initialValue, traits: traits, childBehavior: childBehavior))
    }

    public func pilotAccessibility(
        _ infoFlow: SkieSwiftFlow<PilotAccessibilityInfo>,
        initialValue: PilotAccessibilityInfo,
        traits: AccessibilityTraits = [],
        childBehavior: AccessibilityChildBehavior = .ignore
    ) -> some View {
        modifier(AccessibilityViewModifier(infoFlow: infoFlow, initialValue: initialValue, traits: traits, childBehavior: childBehavior))
    }
}

private struct NullableAccessibilityViewModifier: ViewModifier {
    private let traits: AccessibilityTraits
    private let childBehavior: AccessibilityChildBehavior
    @ObservedObject private var accessibilityInfo: NullableStateObservable<PilotAccessibilityInfo>

    init(
        infoFlow: SkieSwiftOptionalStateFlow<PilotAccessibilityInfo>,
        traits: AccessibilityTraits,
        childBehavior: AccessibilityChildBehavior
    ) {
        _accessibilityInfo = ObservedObject(wrappedValue: NullableStateObservable(infoFlow))
        self.traits = traits
        self.childBehavior = childBehavior
    }

    init(
        info: PilotAccessibilityInfo?,
        traits: AccessibilityTraits,
        childBehavior: AccessibilityChildBehavior
    ) {
        _accessibilityInfo = ObservedObject(wrappedValue: NullableStateObservable(info))
        self.traits = traits
        self.childBehavior = childBehavior
    }

    init(
        infoFlow: SkieSwiftOptionalFlow<PilotAccessibilityInfo>,
        initialValue: PilotAccessibilityInfo?,
        traits: AccessibilityTraits,
        childBehavior: AccessibilityChildBehavior
    ) {
        _accessibilityInfo = ObservedObject(wrappedValue: NullableStateObservable(infoFlow, initialValue: initialValue))
        self.traits = traits
        self.childBehavior = childBehavior
    }

    func body(content: Content) -> some View {
        content
            .configureAccessibility(
                accessibilityInfo: accessibilityInfo.value,
                traits: traits,
                childBehavior: childBehavior
            )
    }
}

private struct AccessibilityViewModifier: ViewModifier {
    private let traits: AccessibilityTraits
    private let childBehavior: AccessibilityChildBehavior
    @ObservedObject private var accessibilityInfo: StateObservable<PilotAccessibilityInfo>

    init(
        infoFlow: SkieSwiftStateFlow<PilotAccessibilityInfo>,
        traits: AccessibilityTraits,
        childBehavior: AccessibilityChildBehavior
    ) {
        _accessibilityInfo = ObservedObject(wrappedValue: StateObservable(infoFlow))
        self.traits = traits
        self.childBehavior = childBehavior
    }

    init(
        infoFlow: SkieSwiftFlow<PilotAccessibilityInfo>,
        initialValue: PilotAccessibilityInfo,
        traits: AccessibilityTraits,
        childBehavior: AccessibilityChildBehavior
    ) {
        _accessibilityInfo = ObservedObject(wrappedValue: StateObservable(infoFlow, initialValue: initialValue))
        self.traits = traits
        self.childBehavior = childBehavior
    }

    func body(content: Content) -> some View {
        content
            .configureAccessibility(
                accessibilityInfo: accessibilityInfo.value,
                traits: traits,
                childBehavior: childBehavior
            )
    }
}

extension View {
    @ViewBuilder
    func configureAccessibility(
        accessibilityInfo: PilotAccessibilityInfo?,
        traits: AccessibilityTraits,
        childBehavior: AccessibilityChildBehavior
    ) -> some View {
        if let accessibilityInfo {
            accessibilityElement(children: childBehavior)
                .accessibilityAddTraits(traits)
                .addIdentifier(accessibilityInfo.identifier)
                .addLabel(accessibilityInfo.label)
                .addHint(accessibilityInfo.hint)
                .addAction(accessibilityInfo.action)
                .addNamedActions(accessibilityInfo.customActions)
        } else {
            self
        }
    }

    @ViewBuilder
    func addIdentifier(_ identifier: String?) -> some View {
        if let identifier {
            accessibilityIdentifier(identifier)
        } else {
            self
        }
    }

    @ViewBuilder
    func addLabel(_ label: String?) -> some View {
        if let label {
            accessibilityLabel(label)
        } else {
            self
        }
    }

    @ViewBuilder
    func addHint(_ hint: String?) -> some View {
        if let hint {
            accessibilityHint(hint)
        } else {
            self
        }
    }

    @ViewBuilder
    func addAction(_ action: (() -> KotlinBoolean)?) -> some View {
        if let action {
            accessibilityAction {
                _ = action()
            }
        } else {
            self
        }
    }

    func addNamedActions(_ actions: [PilotAccessibilityAction]) -> some View {
        actions.reduce(into: AnyView(self)) { partialResult, action in
            partialResult = AnyView(partialResult.accessibilityAction(named: action.label) { action.action() })
        }
    }
}
