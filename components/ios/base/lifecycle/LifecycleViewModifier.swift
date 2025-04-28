import Shared
import SwiftUI

extension View {
    func pilotAppearanceLifecycle(_ appearanceLifecycle: PilotAppearanceLifecycle) -> some View {
        modifier(PilotAppearanceLifecycleViewModifier(appearanceLifecycle: appearanceLifecycle))
    }
}

struct PilotAppearanceLifecycleViewModifier: ViewModifier {
    @Environment(\.scenePhase) var scenePhase
    @Environment(\.presentedPilotRouteName) private var presentedRouteName

    let appearanceLifecycle: PilotAppearanceLifecycle

    @State private var skipFirstScenePhase = true
    @State private var isDisplayed = false
    @State var initialRouteName: String?

    func body(content: Content) -> some View {
        content
            .onChange(of: scenePhase) { newPhase in
                if skipFirstScenePhase {
                    skipFirstScenePhase = false
                } else {
                    if isDisplayed {
                        if newPhase == .active {
                            appearanceLifecycle.onAppear()
                        } else if newPhase == .background {
                            appearanceLifecycle.onDisappear()
                        }
                    }
                }
            }
            .onAppear {
                initialRouteName = presentedRouteName
                isDisplayed = true
                appearanceLifecycle.onAppear()
            }
            .onDisappear {
                isDisplayed = false
                appearanceLifecycle.onDisappear()
            }
            .pilotNavigationRouteNameListener { routeName in
                if isDisplayed {
                    if routeName == initialRouteName {
                        appearanceLifecycle.onAppear()
                    } else {
                        appearanceLifecycle.onDisappear()
                    }
                }
            }
    }
}
