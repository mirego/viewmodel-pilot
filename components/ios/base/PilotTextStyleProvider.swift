import Shared
import SwiftUI

public typealias PilotSpanStyle = [NSAttributedString.Key: Any]

public protocol PilotTextStyleProvider {
    func spanStyle(from resource: PilotTextStyleResource) -> PilotSpanStyle
}

public class DefaultPilotTextStyleProvider: PilotTextStyleProvider {
    var isRunningInPreview: Bool {
        ProcessInfo.processInfo.environment["XCODE_RUNNING_FOR_PREVIEWS"] == "1"
    }

    public func spanStyle(from resource: PilotTextStyleResource) -> PilotSpanStyle {
        guard isRunningInPreview else { fatalError("Pilot span text style not provided") }
        return [:]
    }
}

struct PilotTextStyleProviderKey: EnvironmentKey {
    static let defaultValue: PilotTextStyleProvider = DefaultPilotTextStyleProvider()
}

extension EnvironmentValues {
    public var pilotTextStyleProvider: PilotTextStyleProvider {
        get { self[PilotTextStyleProviderKey.self] }
        set { self[PilotTextStyleProviderKey.self] = newValue }
    }
}
