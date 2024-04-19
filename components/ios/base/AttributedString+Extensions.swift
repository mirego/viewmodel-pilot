import Shared
import SwiftUI

@available(iOS 15, *)
extension AttributedString {
    public init(text: String, ranges: [PilotRichTextRange], styleBuilder: (PilotTextStyleResource) -> PilotSpanStyle) {
        let attributedString = NSMutableAttributedString(string: text)
        for range in ranges {
            switch onEnum(of: range.span) {
            case .style(let style):
                let start = Int(range.start)
                let length = Int(range.end) - start
                let range = NSRange(location: start, length: length)
                attributedString.addAttributes(styleBuilder(style.resource), range: range)
            }
        }
        self.init(attributedString)
    }
}
