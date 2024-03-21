package com.mirego.pilot.components.ui.coil

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.mirego.pilot.components.PilotResizableRemoteImage
import com.mirego.pilot.components.ui.pilotImageResourcePainter

@Composable
public fun PilotResizableRemoteImage(
    pilotRemoteImage: PilotResizableRemoteImage,
    modifier: Modifier = Modifier,
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
    allowHardware: Boolean = true,
) {
    BoxWithConstraints(modifier) {
        with(LocalDensity.current) {
            val url = remember(pilotRemoteImage, maxWidth, maxHeight) {
                val maxWidthPx = maxWidth.takeIf { it != Dp.Infinity && it != Dp.Unspecified }?.roundToPx()
                val maxHeightPx = maxHeight.takeIf { it != Dp.Infinity && it != Dp.Unspecified }?.roundToPx()
                if (maxWidthPx == null && maxHeightPx == null) return@remember null
                pilotRemoteImage.url(maxWidthPx, maxHeightPx)
            }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .allowHardware(allowHardware)
                    .build(),
                contentDescription = pilotRemoteImage.contentDescription,
                modifier = modifier,
                transform = pilotRemoteImage.placeholder
                    ?.let { transformOf(pilotImageResourcePainter(it)) }
                    ?: AsyncImagePainter.DefaultTransform,
                onState = onState,
                contentScale = contentScale,
                alignment = alignment,
                alpha = alpha,
                colorFilter = colorFilter,
                filterQuality = filterQuality,
            )
        }
    }
}
