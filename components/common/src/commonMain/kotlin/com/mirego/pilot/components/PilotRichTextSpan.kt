package com.mirego.pilot.components

public interface PilotTextStyleResource

public data class PilotRichTextRange(val start: Int, val end: Int, val span: PilotRichTextSpan)

public sealed interface PilotRichTextSpan {
    public data class Style(val resource: PilotTextStyleResource) : PilotRichTextSpan
}
