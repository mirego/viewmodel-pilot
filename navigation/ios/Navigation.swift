import SwiftUI
import Shared

public extension View {
    func pilotNavigation<ScreenData, Route: PilotNavigationRoute, Action: AnyObject, ScreenView: View, NavModifier: ViewModifier>(
        navigationManager: PilotNavigationManager<Route, Action>,
        @ViewBuilder buildView: @escaping (ScreenData) -> ScreenView,
        buildNavigation: @escaping ([Route], Route) -> PilotNavigationType<ScreenData, NavModifier>?,
        handleAction: ((Action) -> Void)? = nil,
        handlePopRoot: (() -> Void)? = nil
    ) -> some View {
        modifier(
            NavigationModifier<ScreenData, Route, Action, ScreenView, NavModifier>(
                buildView: buildView,
                buildNavigation: buildNavigation,
                handleAction: handleAction,
                navigationManager: navigationManager,
                handlePopRoot: handlePopRoot
            )
        )
    }
}

private struct NavigationModifier<ScreenData, Route: PilotNavigationRoute, Action: AnyObject, ScreenView: View, NavModifier: ViewModifier>: ViewModifier {
    @StateObject private var rootState: NavigationState<ScreenData, Route, Action, NavModifier>

    @ViewBuilder private let buildView: (ScreenData) -> ScreenView

    init(
        buildView: @escaping (ScreenData) -> ScreenView,
        buildNavigation: @escaping ([Route], Route) -> PilotNavigationType<ScreenData, NavModifier>?,
        handleAction: ((Action) -> Void)? = nil,
        navigationManager: PilotNavigationManager<Route, Action>? = nil,
        handlePopRoot: (() -> Void)?
    ) {
        self.buildView = buildView
        let rootNavigationState = NavigationState<ScreenData, Route, Action, NavModifier>(
            navigation: PilotNavigationType.root,
            route: nil,
            buildNavigation: buildNavigation,
            handleAction: handleAction,
            navigationManager: navigationManager,
            handlePopRoot: handlePopRoot
        )
        _rootState = StateObject(wrappedValue: rootNavigationState)
    }

    func body(content: Content) -> some View {
        NavigationContainerView(navigateState: rootState, buildView: buildView) {
            content
        }
        .onAppear {
            rootState.startListening()
        }
    }
}
