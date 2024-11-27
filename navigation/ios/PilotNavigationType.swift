import Foundation
import SwiftUI

public enum PilotNavigationType<Screen, NavModifier: ViewModifier> {
    case root
    case push(screen: Screen, onDismiss: () -> Void)
    case sheet(screen: Screen, data: NavigationTypeData<NavModifier>)
    @available(iOS 15, tvOS 15, *)
    case fullScreenCover(screen: Screen, data: NavigationTypeData<NavModifier>)
    @available(iOS 15, tvOS 15, *)
    case fullScreenNotAnimated(screen: Screen, data: NavigationTypeData<NavModifier>, popDelayInSeconds: Double?)

    var screen: Screen? {
        switch self {
        case .root:
            return nil
        case .push(let screen, _):
            return screen
        case .sheet(let screen, _):
            return screen
        case .fullScreenCover(let screen, _):
            return screen
        case .fullScreenNotAnimated(let screen, _, _):
            return screen
        }
    }

    var popDelayInSeconds: Double? {
        switch self {
        case .fullScreenNotAnimated(_, _, let popDelayInSeconds):
            return popDelayInSeconds
        default:
            return nil
        }
    }
}

public struct NavigationTypeData<NavModifier: ViewModifier> {
    let embedInNavigationView: Bool
    let onDismiss: () -> Void
    let navigationViewModifier: NavModifier?

    public init(embedInNavigationView: Bool, onDismiss: @escaping () -> Void, navigationViewModifier: NavModifier? = nil) {
        self.embedInNavigationView = embedInNavigationView
        self.onDismiss = onDismiss
        self.navigationViewModifier = navigationViewModifier
    }
}
