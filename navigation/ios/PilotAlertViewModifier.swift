import Shared
import SwiftUI

extension View {
    func alert(with alertData: PilotAlertDialog?, isPresented: Binding<Bool>) -> some View {
        modifier(PilotAlertViewModifier(alertData: alertData, isPresented: isPresented))
    }
}

struct PilotAlertViewModifier: ViewModifier {
    let alertData: PilotAlertDialog?
    let isPresented: Binding<Bool>
    
    func body(content: Content) -> some View {
        if let alertData {
            content
                .alert(
                    alertData.title,
                    isPresented: isPresented,
                    actions: {
                        if let dismissButton = alertData.dismissButton {
                            Button(
                                dismissButton.title,
                                role: .cancel
                            ) {
                                dismissButton.action()
                            }
                        }
                                                
                        Button(
                            alertData.confirmButton.title,
                            role: alertData.confirmButton.isDestructive ? .destructive : nil
                        ) {
                            alertData.confirmButton.action()
                        }
                    },
                    message: {
                        if let message = alertData.text {
                            Text(message)
                        }
                    }
                )
        } else {
            content
        }
    }
}
