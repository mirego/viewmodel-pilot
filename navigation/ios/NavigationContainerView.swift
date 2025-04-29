import SwiftUI
import Shared

struct NavigationContainerView<
    ScreenData,
    Route: PilotNavigationRoute,
    Action: AnyObject,
    Content: View,
    ScreenView: View,
    NavModifier: ViewModifier
>: View {

    @Environment(\.presentedPilotRouteName) private var presentedRouteName

    @ObservedObject private var navigateState: NavigationState<ScreenData, Route, Action, NavModifier>

    @ViewBuilder private let buildView: (ScreenData) -> ScreenView

    let content: () -> Content

    init(navigateState: NavigationState<ScreenData, Route, Action, NavModifier>, buildView: @escaping (ScreenData) -> ScreenView, content: @escaping () -> Content) {
        self.navigateState = navigateState
        self.buildView = buildView
        self.content = content
    }

    var body: some View {
        if embedInNavigationView {
            navigationViewBodyModified
        } else {
            unwrappedBody
        }
    }

    @ViewBuilder
    private var navigationViewBodyModified: some View {
        if let navigationViewModifier {
            navigationViewBody
                .modifier(navigationViewModifier)
        } else {
            navigationViewBody
        }
    }

    @ViewBuilder
    private var navigationViewBody: some View {
        NavigationView {
            unwrappedBody
        }
        #if os(macOS)
        .navigationViewStyle(.automatic)
        #else
        .navigationViewStyle(.stack)
        #endif
    }

    @ViewBuilder
    private var unwrappedBody: some View {
        content()
            .sheet(
                isPresented: sheetBinding,
                onDismiss: childOnDismiss,
                content: { childView }
            )
            .alert(
                with: alertData,
                isPresented: alertBinding
            )
            #if !os(macOS)
            .fullScreenCover(
                isPresented: fullScreenCoverBinding,
                onDismiss: childOnDismiss,
                content: { childView }
            )
            #endif
            .backportNavigationLink(isPresented: pushBinding) {
                childView
            }
            #if canImport(UIKit)
            .background(
                FullScreenNotAnimatedPresenter(
                    isPresented: fullScreenNotAnimatedBinding,
                    onDismiss: childOnDismiss,
                    content: { childView }
                )
            )
            #endif
            .environment(\.pilotNavigationDismissTriggered, navigateState.navigationDismissTriggered)
            .environment(\.presentedPilotRouteName, navigateState.child?.route?.name ?? presentedRouteName)
    }

    private var embedInNavigationView: Bool {
        switch navigateState.navigation {
        case .root:
            return false
        case .push:
            return false
        case .sheet(_, let data):
            return data.embedInNavigationView
        case .fullScreenCover(_, let data):
            return data.embedInNavigationView
        case .fullScreenNotAnimated(_, let data, _):
            return data.embedInNavigationView
        case .alert:
            return false
        }
    }

    private var navigationViewModifier: NavModifier? {
        switch navigateState.navigation {
        case .fullScreenCover(_, let data):
            return data.navigationViewModifier
        default:
            return nil
        }
    }

    @ViewBuilder
    private var childView: some View {
        if let child = navigateState.child, let screen = child.navigation.screen {
            NavigationContainerView<ScreenData, Route, Action, ScreenView, ScreenView, NavModifier>(
                navigateState: child,
                buildView: buildView
            ) {
                buildView(screen)
            }
        } else {
            EmptyView()
        }
    }

    private var isActiveBinding: Binding<Bool> {
        Binding(
            get: {
                navigateState.child != nil
            },
            set: { isShowing in
                if !isShowing {
                    if isChildDismissHandlerManuallyTriggered {
                        childOnDismiss?()
                    }
                    navigateState.child = nil
                }
            }
        )
    }

    private var sheetBinding: Binding<Bool> {
        guard let child = navigateState.child else { return .constant(false) }
        switch child.navigation {
        case .sheet:
            return isActiveBinding
        default:
            return .constant(false)
        }
    }

    private var fullScreenCoverBinding: Binding<Bool> {
        guard let child = navigateState.child else { return .constant(false) }
        switch child.navigation {
        case .fullScreenCover:
            return isActiveBinding
        default:
            return .constant(false)
        }
    }

    private var fullScreenNotAnimatedBinding: Binding<Bool> {
        guard let child = navigateState.child else { return .constant(false) }
        switch child.navigation {
        case .fullScreenNotAnimated:
            return isActiveBinding
        default:
            return .constant(false)
        }
    }

    private var pushBinding: Binding<Bool> {
        guard let child = navigateState.child else { return .constant(false) }
        switch child.navigation {
        case .push:
            return isActiveBinding
        default:
            return .constant(false)
        }
    }
    
    private var alertBinding: Binding<Bool> {
        guard let child = navigateState.child else { return .constant(false) }
        switch child.navigation {
        case .alert:
            return isActiveBinding
        default:
            return .constant(false)
        }
    }

    private var childOnDismiss: (() -> Void)? {
        guard let child = navigateState.child else { return nil }
        switch child.navigation {
        case .root:
            return nil
        case .push(_, let onDismiss):
            return onDismiss
        case .sheet(_, let data):
            return data.onDismiss
        case .fullScreenCover(_, let data):
            return data.onDismiss
        case .fullScreenNotAnimated(_, let data, _):
            return data.onDismiss
        case .alert(let data):
            return data.onDismiss
        }
    }

    private var isChildDismissHandlerManuallyTriggered: Bool {
        guard let child = navigateState.child else { return false }
        switch child.navigation {
        case .push:
            return true
        case .alert:
            return true
        default:
            return false
        }
    }
    
    private var alertData: PilotAlertDialog? {
        guard let child = navigateState.child else { return nil }
        switch child.navigation {
        case .alert(let data):
            return data
        default:
            return nil
        }
    }
}

private extension View {
    @ViewBuilder
    func backportNavigationLink<V>(isPresented: Binding<Bool>, @ViewBuilder destination: () -> V) -> some View where V: View {
        background(
            NavigationLink(destination: destination(), isActive: isPresented, label: EmptyView.init)
                .hidden()
        )
    }
}
