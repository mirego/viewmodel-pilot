import Shared
import SwiftUI

extension PilotKeyboardReturnKeyType {
    @available(iOS 15.0, *)
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
