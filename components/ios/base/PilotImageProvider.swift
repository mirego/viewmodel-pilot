import Shared
import SwiftUI

public protocol PilotImageProvider {
    func image(from resource: PilotImageResource) -> Image
}

public class DefaultPilotImageProvider: PilotImageProvider {
    private var isRunningInPreview: Bool {
        ProcessInfo.processInfo.environment["XCODE_RUNNING_FOR_PREVIEWS"] == "1"
    }

    public func image(from resource: PilotImageResource) -> Image {
        guard isRunningInPreview else { fatalError("Pilot Image builder not provided") }
        return Image(systemName: "exclamationmark.brakesignal")
    }
}

struct PilotImageProviderKey: EnvironmentKey {
    static let defaultValue: PilotImageProvider = DefaultPilotImageProvider()
}

extension EnvironmentValues {
    public var pilotImageProvider: PilotImageProvider {
        get { self[PilotImageProviderKey.self] }
        set { self[PilotImageProviderKey.self] = newValue }
    }
}
