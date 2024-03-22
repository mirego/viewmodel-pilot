import Shared
import SwiftUI

public struct PilotRichTextView: View {
    @ObservedObject private var richText: StateObservable<PilotRichText>

    @Environment(\.pilotImageProvider) var pilotImageProvider
    @Environment(\.pilotTextStyleProvider) var pilotTextStyleProvider

    private let imageModifier: (Image) -> Image
    private let leadingImagePadding: (PilotImageResource) -> CGFloat
    private let trailingImagePadding: (PilotImageResource) -> CGFloat

    public init(
        _ richText: PilotRichText,
        leadingImagePadding: @escaping (PilotImageResource) -> CGFloat = { _ in 0 },
        trailingImagePadding: @escaping (PilotImageResource) -> CGFloat = { _ in 0 },
        imageModifier: @escaping (Image) -> Image = { $0 }
    ) {
        _richText = ObservedObject(wrappedValue: StateObservable(richText))
        self.leadingImagePadding = leadingImagePadding
        self.trailingImagePadding = trailingImagePadding
        self.imageModifier = imageModifier
    }

    public init(
        _ richText: SkieSwiftStateFlow<PilotRichText>,
        leadingImagePadding: @escaping (PilotImageResource) -> CGFloat = { _ in 0 },
        trailingImagePadding: @escaping (PilotImageResource) -> CGFloat = { _ in 0 },
        imageModifier: @escaping (Image) -> Image = { $0 }
    ) {
        _richText = ObservedObject(wrappedValue: StateObservable(richText))
        self.leadingImagePadding = leadingImagePadding
        self.trailingImagePadding = trailingImagePadding
        self.imageModifier = imageModifier
    }

    public var body: some View {
        richText.value.contents.reduce(Text("")) { result, item -> Text in
            result + itemTextView(item)
        }
    }

    private func itemTextView(_ item: PilotRichTextContent) -> Text {
        switch onEnum(of: item) {
        case .text(let text):
            return Text(AttributedString(text: text.text, ranges: text.ranges) { pilotTextStyleProvider.spanStyle(from: $0) })
        case .image(let image):
            var textViews = [Text(imageModifier(pilotImageProvider.image(from: image.image)))]
            let leading = leadingImagePadding(image.image)
            if leading > 0 {
                textViews.insert(Text(createClearImage(width: leading)), at: 0)
            }
            let trailing = trailingImagePadding(image.image)
            if trailing > 0 {
                textViews.append(Text(createClearImage(width: trailing)))
            }
            return textViews.reduce(Text("")) { result, item -> Text in
                result + item
            }
        }
    }
}

private func createClearImage(width: CGFloat) -> Image {
    let size = CGSize(width: width, height: 1)
    return Image(
        uiImage: UIGraphicsImageRenderer(size: size).image { context in
            context.cgContext.setFillColor(UIColor.clear.cgColor)
            context.cgContext.addRect(CGRect(origin: .zero, size: size))
            context.cgContext.drawPath(using: .fill)
        }
    )
}
