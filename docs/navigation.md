# Navigation

## Usage

### Common

First you must create a `NavigationRoute` sealed class that will represent all the possible routes
of your application.

```kotlin
enum class YourNavigationRouteName {
    ROUTE1,
    ROUTE2
}

sealed class YourNavigationRoute(routeName: YourNavigationRouteName) :
    EnumPilotNavigationRoute(routeName) {
    data object Route1 : YourNavigationRoute(ROUTE1)
    data class Route2(val someNavigationData: SomeNavigationData) : YourNavigationRoute(ROUTE2)
}
```

You must also create a `NavigationAction` sealed class that will represent all the possible actions
of your application.

```kotlin
sealed interface YourNavigationAction {
    data object Action1 : YourNavigationAction
    data class Action2(val someActionArgument: SomeActionArgument) : YourNavigationAction
}
```

Then you must create a `NavigationManager` class which will allow you to navigate between routes.
Navigation managers support a parent navigation manager which allows you to create a hierarchy of
navigation managers.

```kotlin
class YourNavigationManager(
    coroutineScope: CoroutineScope,
    parentNavigationManager: YourNavigationManager? = null,
) : DefaultPilotNavigationManager<YourNavigationRoute, YourNavigationAction>(
    coroutineScope,
    parentNavigationManager,
)
```

### Android

You must create a `YourActionNavigationListener` which will implement your external navigation
actions.

```kotlin
class YourActionNavigationListener : PilotActionNavigationListener<YourNavigationAction> {
    override fun handleAction(action: NewsNavigationAction) {
        when (action) {
            YourNavigationAction.Action1 -> {
                // Your code here
            }
            is YourNavigationAction.Action2 -> {
                // Your code here
            }
        }
    }
}
```

You must create a `NavigationHost` composable that will be the entry point of your application.

```kotlin
@Composable
fun YourNavigationHost(
    navigationManager: YourNavigationManager,
    modifier: Modifier = Modifier,
    rootContent: @Composable () -> Unit,
) {
    val navController = rememberNavController()

    LaunchedEffect(navigationManager, navController) {
        navigationManager.listener = PilotNavControllerNavigationListener(navController)
        navigationManager.actionListener = YourActionNavigationListener()
    }

    NavHost(
        navController = navController,
        startDestination = ROOT_ROUTE,
    ) {
        composable(ROOT_ROUTE) {
            rootContent()
        }
        composable(YourNavigationRoute.ROUTE1.name) {
            YourRoute1Screen(navigationManager)
        }
        composable(YourNavigationRoute.ROUTE2.name) {
            YourRoute2Screen(navigationManager)
        }
    }

    PilotBackHandler(
        navController = navController,
        navigationManager = navigationManager,
        rootName = ROOT_ROUTE,
    )
}
```

### iOS

SwiftUI navigation works through the help of a View Modifier.

```swift
extension View {
    func yourNavigation(navigationManager: YourNavigationManager) -> some View {
        modifier(YourNavigationModifier(navigationManager: navigationManager))
    }
}

private struct YourNavigationModifier: ViewModifier {
    let navigationManager: YourNavigationManager

    func body(content: Content) -> some View {
        content
            .pilotNavigation(
                navigationManager: navigationManager,
                buildView: buildView,
                buildNavigation: buildNavigation,
                handleAction: handleAction
            )
    }

    @ViewBuilder
    private func buildView(viewModelHolder: ViewModelHolder) -> some View {
        switch viewModelHolder {
        case .route1(let viewModel):
            Route1View(viewModel: viewModel)
        case .route2(let viewModel):
            Route2View(viewModel: viewModel)
    }

    private func buildNavigation(routes: [YourNavigationRoute], route: YourNavigationRoute) -> PilotNavigationType<ViewModelHolder, EmptyViewModifier>? {
        let onDismissClosure: () -> Void = {
            navigationManager.poppedFrom(route: route)
        }
        
        return switch onEnum(of: route) {
        case .route1(let route):
                .sheet(
                    screen: ViewModelHolder.route1(
                        // Create your view model here with the route
                    ),
                    data: NavigationTypeData(embedInNavigationView: false, onDismiss: onDismissClosure)
                )
        case .route2(let route):
                .fullScreenCover(
                    screen: ViewModelHolder.route2(
                        // Create your view model here with the route
                    ),
                    data: NavigationTypeData(embedInNavigationView: false, onDismiss: onDismissClosure)
                )
    }

    private func handleAction(action: YourNavigationAction) {
        switch onEnum(of: action) {
        case .action1:
            // Your code here
        case .action2(let action):
            // Your code here
    }
}

enum ViewModelHolder {
    case route1(Route1ViewModel)
    case route2(Route2ViewModel)
}
```

## Installation

### Common / Android

`build.gradle.kts`

```kotlin
repositories {
    maven(url = "https://s3.amazonaws.com/mirego-maven/public")
}

dependencies {
    implementation("com.mirego.pilot:navigation:<version>")
}
```

## Limitations

!!! warning This library does not support restoring the state of the navigation stack when the
application is killed and restarted on android. This is a known limitation and is not planned to be
implemented in the future.

`Podfile`

```ruby
pod 'Pilot/Navigation', :git => 'git@github.com:mirego/viewmodel-pilot.git', :tag => '<version>', :inhibit_warnings => true
```