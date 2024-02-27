import SwiftUI

struct PilotNavigationDismissTriggeredKey: EnvironmentKey {
    static let defaultValue = false
}

public extension EnvironmentValues {
    var pilotNavigationDismissTriggered: Bool {
        get { self[PilotNavigationDismissTriggeredKey.self] }
        set { self[PilotNavigationDismissTriggeredKey.self] = newValue }
    }
}

struct PresentedPilotRouteNameKey: EnvironmentKey {
    static let defaultValue: String? = nil
}

public extension EnvironmentValues {
    var presentedPilotRouteName: String? {
        get { self[PresentedPilotRouteNameKey.self] }
        set { self[PresentedPilotRouteNameKey.self] = newValue }
    }
}
