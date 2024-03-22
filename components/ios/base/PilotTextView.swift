import Shared
import SwiftUI

public struct PilotTextView: View {
    @ObservedObject private var text: StateObservable<String>

    public init(_ text: SkieSwiftStateFlow<String>) {
        _text = ObservedObject(wrappedValue: StateObservable(text))
    }

    public var body: some View {
        Text(text.value)
    }
}
