import Shared
import SwiftUI

extension PilotKeyboardReturnKeyType {
    public var submitLabel: SubmitLabel {
        switch self {
        case .default:
            return .done
        case .done:
            return .done
        case .go:
            return .go
        case .next:
            return .next
        case .search:
            return .search
        case .send:
            return .send
        }
    }
}
