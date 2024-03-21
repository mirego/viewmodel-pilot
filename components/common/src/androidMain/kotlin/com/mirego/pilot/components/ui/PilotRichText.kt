package com.mirego.pilot.components.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import com.mirego.pilot.components.InternalPilotComponentsApi
import com.mirego.pilot.components.PilotRichText
import com.mirego.pilot.components.PilotRichTextRange
import com.mirego.pilot.components.PilotRichTextSpan

@Composable
@InternalPilotComponentsApi
public fun PilotRichText.toAnnotatedString(textStyleResourceProvider: PilotTextStyleResourceProvider): AnnotatedString =
    buildAnnotatedString {
        contents.forEach { content ->
            when (content) {
                is PilotRichText.Content.Image -> addImage(content)
                is PilotRichText.Content.Text -> addText(content, textStyleResourceProvider)
            }
        }
    }

@InternalPilotComponentsApi
public fun PilotRichText.toBasicInlineContent(inlineTextContent: (PilotRichText.Content.Image) -> InlineTextContent): Map<String, InlineTextContent> =
    contents.filterIsInstance<PilotRichText.Content.Image>()
        .associate { image ->
            image.id to inlineTextContent(image)
        }

@InternalPilotComponentsApi
public fun defaultImageInlineTextContent(placeholder: Placeholder, image: PilotRichText.Content.Image, imageModifier: Modifier = Modifier): InlineTextContent =
    InlineTextContent(
        placeholder = placeholder,
        children = @Composable {
            Image(
                painter = pilotImageResourcePainter(image.image),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = imageModifier
                    .fillMaxSize(),
            )
        },
    )

@Composable
@SuppressLint("ComposableNaming")
private fun AnnotatedString.Builder.addText(text: PilotRichText.Content.Text, textStyleResourceProvider: PilotTextStyleResourceProvider) {
    append(text.text)
    text.ranges.forEach { addRangeStyle(it, textStyleResourceProvider) }
}

private fun AnnotatedString.Builder.addImage(image: PilotRichText.Content.Image) {
    appendInlineContent(image.id)
}

@Composable
@SuppressLint("ComposableNaming")
private fun AnnotatedString.Builder.addRangeStyle(range: PilotRichTextRange, textStyleResourceProvider: PilotTextStyleResourceProvider) {
    range.toComposeSpanStyle(textStyleResourceProvider)?.let {
        addStyle(
            style = it,
            start = range.start,
            end = range.end,
        )
    }
}

@Composable
private fun PilotRichTextRange.toComposeSpanStyle(textStyleResourceProvider: PilotTextStyleResourceProvider): SpanStyle? =
    when (val currentSpan = span) {
        is PilotRichTextSpan.Style -> textStyleResourceProvider.textStyleForResource(currentSpan.resource)?.toSpanStyle()
        else -> null
    }
