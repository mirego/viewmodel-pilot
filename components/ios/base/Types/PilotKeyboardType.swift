import Shared
import SwiftUI

extension PilotKeyboardType {
    public var uiKeyboardType: UIKeyboardType {
        switch self {
        case .default:
            return .default
        case .ascii:
            return .asciiCapable
        case .number:
            return .numberPad
        case .email:
            return .emailAddress
        case .password:
            return .default
        case .numberPassword:
            return .numberPad
        case .phone:
            return .phonePad
        case .url:
            return .URL
        }
    }
}
