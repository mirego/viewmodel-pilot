import Kingfisher
import Shared
import SwiftUI

public struct PilotRemoteImageView: View {
    public typealias ImageConfiguration = (KFImage, Image?) -> KFImage

    private enum Content {
        case normal(PilotRemoteImage)
        case resizable(PilotResizableRemoteImage, CGSize)
    }

    private var imageConfigurations = [ImageConfiguration]()

    private let content: Content

    @Environment(\.displayScale) private var displayScale

    @Environment(\.pilotImageProvider) private var pilotImageProvider

    @State var loadingStatus: PilotRemoteImageStatus = .loading

    public init(_ content: PilotRemoteImage) {
        self.content = .normal(content)
    }

    public init(_ content: PilotResizableRemoteImage, size: CGSize) {
        self.content = .resizable(content, size)
    }

    public var body: some View {
        imageConfigurations.reduce(
            KFImage(imageURL)
                .onFailure { _ in
                    loadingStatus = .error
                }
                .onSuccess { _ in
                    loadingStatus = .success
                }
        ) { current, config in
            if let placeholder = placeholder {
                config(current, pilotImageProvider.image(from: placeholder))
            } else {
                config(current, nil)
            }
        }
        .accessibilityLabel(contentDescription ?? "")
        .environment(\.pilotRemoteImageStatus, loadingStatus)
    }

    public func configure(_ block: @escaping ImageConfiguration) -> PilotRemoteImageView {
        var result = self
        result.imageConfigurations.append(block)
        return result
    }

    private var imageURL: URL? {
        switch content {
        case .normal(let pilotRemoteImage):
            stringToURL(pilotRemoteImage.url)
        case let .resizable(pilotResizableRemoteImage, size):
            stringToURL(
                pilotResizableRemoteImage.url(
                    width: KotlinInt(int: Int32(size.width * displayScale)),
                    height: KotlinInt(int: Int32(size.height * displayScale))
                )
            )
        }
    }

    private var placeholder: PilotImageResource? {
        switch content {
        case .normal(let pilotRemoteImage):
            pilotRemoteImage.placeholder
        case .resizable(let pilotResizableRemoteImage, _):
            pilotResizableRemoteImage.placeholder
        }
    }

    private var contentDescription: String? {
        switch content {
        case .normal(let pilotRemoteImage):
            pilotRemoteImage.contentDescription
        case .resizable(let pilotResizableRemoteImage, _):
            pilotResizableRemoteImage.contentDescription
        }
    }

    private func stringToURL(_ stringUrl: String?) -> URL? {
        if let stringUrl, let imageURL = URL(string: stringUrl) {
            return imageURL
        } else {
            return nil
        }
    }
}

extension PilotRemoteImageView {
    public func resizable() -> PilotRemoteImageView {
        configure { kfImage, _ in
            kfImage.resizable()
        }
    }

    public func renderingMode(_ renderingMode: Image.TemplateRenderingMode) -> PilotRemoteImageView {
        configure { kfImage, _ in
            kfImage.renderingMode(renderingMode)
        }
    }

    public func placeholder<Content: View>(@ViewBuilder _ content: @escaping (_ placeholderImage: Image?) -> Content) -> PilotRemoteImageView {
        configure { kfImage, placehoder in
            kfImage.placeholder { _ in
                content(placehoder)
            }
        }
    }
}

public enum PilotRemoteImageStatus {
    case loading
    case success
    case error
}

public struct PilotRemoteImageStatusKey: EnvironmentKey {
    public static let defaultValue = PilotRemoteImageStatus.loading
}

extension EnvironmentValues {
    public var pilotRemoteImageStatus: PilotRemoteImageStatus {
        get { self[PilotRemoteImageStatusKey.self] }
        set { self[PilotRemoteImageStatusKey.self] = newValue }
    }
}
