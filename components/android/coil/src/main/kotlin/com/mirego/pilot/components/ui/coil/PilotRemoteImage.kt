package com.mirego.pilot.components.ui.coil

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.mirego.pilot.components.PilotRemoteImage
import com.mirego.pilot.components.ui.pilotImageResourcePainter

@Composable
public fun PilotRemoteImage(
    pilotRemoteImage: PilotRemoteImage,
    modifier: Modifier = Modifier,
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
    allowHardware: Boolean = true,
    asyncImageBuilder: ImageRequest.Builder.() -> Unit = {},
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(pilotRemoteImage.url)
            .allowHardware(allowHardware)
            .apply(asyncImageBuilder)
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

@Stable
internal fun transformOf(placeholder: Painter): (AsyncImagePainter.State) -> AsyncImagePainter.State =
    { state ->
        when (state) {
            is AsyncImagePainter.State.Loading -> if (state.painter == null) state.copy(painter = placeholder) else state
            is AsyncImagePainter.State.Error -> if (state.painter == null) state.copy(painter = placeholder) else state
            else -> state
        }
    }
