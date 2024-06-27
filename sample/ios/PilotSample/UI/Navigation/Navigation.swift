import Pilot
import Shared
import SwiftUI

extension View {
    func navigation(navigationManager: NavigationManager, embedInNavigationView: Bool = false) -> some View {
        modifier(NavigationModifier(navigationManager: navigationManager, embedInNavigationView: embedInNavigationView))
    }
}

private struct NavigationModifier: ViewModifier {
    let navigationManager: NavigationManager
    let embedInNavigationView: Bool

    func body(content: Content) -> some View {
        content
            .pilotNavigation(
                navigationManager: navigationManager,
                buildView: buildView,
                buildNavigation: buildNavigation,
                handleAction: handleAction
            )
            .if(embedInNavigationView) {
                $0.embedInNavigationView()
            }
    }

    @ViewBuilder
    private func buildView(viewModelHolder: ViewModelHolder) -> some View {
        switch viewModelHolder {
        case .richText(let viewModel):
            Text("Rich Text")
        case .textField(let viewModel):
            Text("TextField")
        case .others(let viewModel):
            Text("Others")
        }
    }

    private func buildNavigation(routes: [NavigationRoute], route: NavigationRoute) -> PilotNavigationType<ViewModelHolder, EmptyViewModifier>? {
        let onDismissClosure: () -> Void = {
            navigationManager.poppedFrom(route: route)
        }

        return switch onEnum(of: route) {
        case .richTextPage:
                .sheet(
                    screen: ViewModelHolder.richText(RichTextPageViewModelImpl(navigationManager: navigationManager)),
                    data: NavigationTypeData(embedInNavigationView: false, onDismiss: onDismissClosure)
                )
        case .textFieldPage:
                .sheet(
                    screen: ViewModelHolder.textField(TextFieldPageViewModelImpl(navigationManager: navigationManager)),
                    data: NavigationTypeData(embedInNavigationView: false, onDismiss: onDismissClosure)
                )
        case .otherComponentsPage:
                .fullScreenCover(
                    screen: ViewModelHolder.others(OtherComponentsPageViewModelImpl(navigationManager: navigationManager)),
                    data: NavigationTypeData(embedInNavigationView: false, onDismiss: onDismissClosure)
                )
        }
    }

    private func handleAction(action: NavigationAction) {
        switch onEnum(of: action) {
        case .else:
            break
        }
    }
}


enum ViewModelHolder {
    case richText(RichTextPageViewModel)
    case textField(TextFieldPageViewModel)
    case others(OtherComponentsPageViewModel)
}
