package com.mirego.pilot.components.ui.material3

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.takeOrElse
import com.mirego.pilot.components.ui.LocalPilotTextStyleResourceProvider
import com.mirego.pilot.components.PilotRichText
import com.mirego.pilot.components.ui.defaultImageInlineTextContent
import com.mirego.pilot.components.ui.toAnnotatedString
import com.mirego.pilot.components.ui.toBasicInlineContent

@Composable
public fun PilotRichText(
    richText: PilotRichText,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    inlineTextContent: (PilotRichText.Content.Image) -> InlineTextContent = { image ->
        val placeholderSize = fontSize.takeOrElse { style.fontSize }.takeOrElse { 14.sp }
        defaultImageInlineTextContent(
            image = image,
            placeholder = Placeholder(
                // 1.5f gives us a little extra horizontal padding by default
                width = placeholderSize * 1.5f,
                height = placeholderSize,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
            ),
        )
    },
) {
    Text(
        text = richText.toAnnotatedString(LocalPilotTextStyleResourceProvider.current),
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        inlineContent = richText.toBasicInlineContent(inlineTextContent),
        onTextLayout = onTextLayout,
        style = style,
    )
}
