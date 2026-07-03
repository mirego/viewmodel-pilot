import Shared
import SwiftUI

public struct PilotLocalImage: View {
    public typealias ImageConfiguration = (Image) -> Image

    private var imageConfigurations: [ImageConfiguration] = []

    @ObservedObject private var content: StateObservable<PilotLocalImageContent>

    @Environment(\.pilotImageProvider) private var pilotImageProvider

    public init(_ content: SkieSwiftStateFlow<PilotLocalImageContent>) {
        _content = ObservedObject(wrappedValue: StateObservable(content))
    }

    public init(_ content: PilotLocalImageContent) {
        _content = ObservedObject(wrappedValue: StateObservable(content))
    }

    public var body: some View {
        imageConfigurations.reduce(pilotImageProvider.image(from: content.value.imageResource)) { current, config in
            config(current)
        }
        .accessibilityLabel(content.value.contentDescription ?? "")
    }

    public func configureImage(_ block: @escaping ImageConfiguration) -> PilotLocalImage {
        var result = self
        result.imageConfigurations.append(block)
        return result
    }
}

extension PilotLocalImage {
    public func resizable() -> PilotLocalImage {
        configureImage {
            $0.resizable()
        }
    }

    public func renderingMode(_ renderingMode: Image.TemplateRenderingMode) -> PilotLocalImage {
        configureImage {
            $0.renderingMode(renderingMode)
        }
    }
}
