import Shared
import SwiftUI
/*
    Since PilotNavigationListener is a generic Objective-C class we must bind type parameters
    of PilotNavigationListener to specific concrete types (PilotNavigationRoute).
    And this is why we have to cast the PilotNavigationRoute to Route in push and popTo methods
 */
class NavigationState<
    ScreenData,
    Route: PilotNavigationRoute,
    Action: AnyObject,
    NavModifier: ViewModifier
>: PilotNavigationListener<PilotNavigationRoute>, ObservableObject {

    typealias NavigationStateTyped = NavigationState<ScreenData, Route, Action, NavModifier>

    let navigation: PilotNavigationType<ScreenData, NavModifier>
    let route: Route?

    @Published var child: NavigationStateTyped?
    @Published var navigationDismissTriggered = false

    private let buildNavigation: (([Route], Route) -> PilotNavigationType<ScreenData, NavModifier>)?
    private let navigationManager: PilotNavigationManager<Route, Action>?
    private let actionListener: ActionListener<Route, Action>
    private var lastNavigationDate: Foundation.Date?

    init(
        navigation: PilotNavigationType<ScreenData, NavModifier>,
        route: Route?,
        buildNavigation: (([Route], Route) -> PilotNavigationType<ScreenData, NavModifier>)? = nil,
        handleAction: ((Action) -> Void)? = nil,
        navigationManager: PilotNavigationManager<Route, Action>? = nil
    ) {
        self.navigation = navigation
        self.route = route
        self.buildNavigation = buildNavigation
        self.navigationManager = navigationManager
        actionListener = ActionListener(navigationManager: navigationManager, handleAction: handleAction)

        super.init()
    }

    func startListening() {
        navigationManager?.listener = self as? PilotNavigationListener<Route>
        actionListener.startListening()
    }

    override func push(route: PilotNavigationRoute) {
        guard let buildNavigation else { fatalError("buildNavigation not set")}
        guard let route = route as? Route else { fatalError("Invalid route type")}

        dedounceNavigation { [weak self] in
            guard let self else { return }
            top().child = NavigationState(navigation: buildNavigation(currentStack(), route), route: route)
        }
    }

    override func popTo(route: PilotNavigationRoute, inclusive: Bool) {
        guard let route = route as? Route else { fatalError("Invalid route type") }

        dedounceNavigation { [weak self] in
            guard let self else { return }
            if let routeNavigationState = findLast(route: route) {
                if inclusive {
                    findParent(state: routeNavigationState)?.child = nil
                } else {
                    routeNavigationState.child = nil
                }
            }
        }
    }

    override func pop() {
        dedounceNavigation { [weak self] in
            if let topPresenter = self?.topPresenter() {
                if let popDelayInSeconds = topPresenter.child?.navigation.popDelayInSeconds {
                    topPresenter.child?.navigationDismissTriggered = true
                    DispatchQueue.main.asyncAfter(deadline: .now() + popDelayInSeconds) {
                        topPresenter.child = nil
                    }
                } else {
                    topPresenter.child = nil
                }
            }
        }
    }

    private func dedounceNavigation(action: @escaping () -> Void) {
        let navigationAnimationDuration = 0.55

        if let lastNavigationDate {
            let timeIntervalSinceNow = -lastNavigationDate.timeIntervalSinceNow
            if timeIntervalSinceNow < navigationAnimationDuration {
                self.lastNavigationDate = Date(timeIntervalSinceNow: (navigationAnimationDuration - timeIntervalSinceNow))
                DispatchQueue.main.asyncAfter(deadline: .now() + (navigationAnimationDuration - timeIntervalSinceNow)) {
                    action()
                }
            } else {
                self.lastNavigationDate = Date()
                action()
            }
        } else {
            self.lastNavigationDate = Date()
            action()
        }
    }

    private func top() -> NavigationStateTyped {
        var current = self

        while current.child != nil {
            current = current.child!
        }
        return current
    }

    private func topPresenter() -> NavigationStateTyped? {
        guard self.child != nil else { return nil }

        var current = self
        while current.child?.child != nil {
            current = current.child!
        }
        return current
    }

    private func findLast(route: Route) -> NavigationStateTyped? {
        var current: NavigationStateTyped? = self
        var result: NavigationStateTyped?
        repeat {
            if current?.route?.uniqueId == route.uniqueId {
                result = current
            }
            current = current?.child
        } while current != nil

        return result
    }

    private func findParent(state: NavigationStateTyped) -> NavigationStateTyped? {
        var current = self
        while current.child != nil {
            if state === current.child {
                return current
            }
            current = current.child!
        }
        return nil
    }

    private func currentStack() -> [Route] {
        var stack: [Route] = []
        var current: NavigationStateTyped? = self
        while current != nil {
            if let route = current?.route {
                stack.append(route)
            }
            current = current?.child
        }
        return stack
    }
}

/*
    Since PilotActionNavigationListener is a generic Objective-C class we must bind type parameters
    of PilotActionNavigationListener to specific concrete types (AnyObject).
    And this is why we have to cast the AnyObject to Action
 */

private class ActionListener<Route: PilotNavigationRoute, Action: AnyObject>: PilotActionNavigationListener<AnyObject> {
    private let navigationManager: PilotNavigationManager<Route, Action>?
    private let handleAction: ((Action) -> Void)?

    init(
        navigationManager: PilotNavigationManager<Route, Action>? = nil,
        handleAction: ((Action) -> Void)?
    ) {
        self.navigationManager = navigationManager
        self.handleAction = handleAction
        super.init()
    }

    func startListening() {
        navigationManager?.actionListener = self as? PilotActionNavigationListener<Action>
    }

    override func handleAction(action: AnyObject?) {
        guard let action = action as? Action else { fatalError("invalid action type") }
        handleAction?(action)
    }
}
