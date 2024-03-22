import Shared
import SwiftUI

extension PilotKeyboardAutoCapitalization {
    public var uiTextAutocapitalizationType: UITextAutocapitalizationType {
        switch self {
        case .none:
            return .none
        case .characters:
            return .allCharacters
        case .sentences:
            return .sentences
        case .words:
            return .words
        }
    }
}
