import SwiftUI
import SwiftUI_Utils

extension View {
    func embedInNavigationView(enableNativeSwipeBack: Bool = true) -> some View {
        modifier(EmbedInNavigationViewModifier(enableNativeSwipeBack: enableNativeSwipeBack))
    }
}

private struct EmbedInNavigationViewModifier: ViewModifier {
    let enableNativeSwipeBack: Bool

    func body(content: Content) -> some View {
        NavigationView {
            content
                .navigationBarBackButtonHidden(true)
                .navigationBarTitleDisplayMode(.inline)
        }
        .navigationViewStyle(.stack)
        .if(enableNativeSwipeBack) {
            $0.enableNativeSwipeBack()
        }
    }
}
