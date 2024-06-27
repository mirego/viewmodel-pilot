import SwiftUI
import SwiftUIIntrospect

extension View {
    func enableNativeSwipeBack() -> some View {
        modifier(NativeSwipeBackModifier())
    }
}

private struct NativeSwipeBackModifier: ViewModifier {
    func body(content: Content) -> some View {
        content
            .introspect(.navigationView(style: .stack), on: .iOS(.v16, .v17)) {
                $0.interactivePopGestureRecognizer?.delegate = nil
            }
    }
}
