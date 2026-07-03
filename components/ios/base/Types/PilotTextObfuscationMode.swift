import PILOT_FRAMEWORK_NAME
import SwiftUI

extension PilotTextObfuscationMode: Equatable {
    public static func == (lhs: PilotTextObfuscationMode, rhs: PilotTextObfuscationMode) -> Bool {
        switch (lhs, rhs) {
        case (.hidden, .hidden), (.visible, .visible):
            return true
        default:
            return false
        }
    }
}
