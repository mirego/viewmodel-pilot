package com.mirego.pilot.components

public data class PilotRichText(
    val contents: List<Content>,
) {
    public sealed interface Content {
        public data class Text(val text: String, val ranges: List<PilotRichTextRange>) : Content
        public data class Image(val image: PilotImageResource, val id: String = image.toString()) : Content
    }

    public class Builder {
        private val contents = mutableListOf<Content>()

        public fun appendText(builder: (TextBuilder).() -> Unit) {
            contents.add(TextBuilder().apply(builder).toContent())
        }

        public fun appendImage(image: PilotImageResource) {
            contents.add(Content.Image(image))
        }

        public fun toRichText(): PilotRichText =
            PilotRichText(contents)
    }

    public class TextBuilder {
        private val sb = StringBuilder()
        private val ranges = mutableListOf<PilotRichTextRange>()

        public val length: Int get() = sb.length

        public fun append(str: String): TextBuilder {
            sb.append(str)
            return this
        }

        public fun addStyle(resource: PilotTextStyleResource, start: Int, end: Int) {
            ranges.add(PilotRichTextRange(start, end, PilotRichTextSpan.Style(resource)))
        }

        public fun addStyle(resource: PilotTextStyleResource, styledText: String) {
            val startIndex = sb.indexOf(styledText)
            if (startIndex == -1) {
                return
            }
            addStyle(resource, startIndex, startIndex + styledText.length)
        }

        public fun toContent(): Content.Text =
            Content.Text(
                sb.toString(),
                ranges,
            )
    }
}

public inline fun buildRichText(builder: (PilotRichText.Builder).() -> Unit): PilotRichText =
    PilotRichText.Builder().apply(builder).toRichText()
