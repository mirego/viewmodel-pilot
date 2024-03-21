package com.mirego.pilot.components.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import com.mirego.pilot.components.PilotImageResource
import com.mirego.pilot.components.PilotTextStyleResource

public interface PilotTextStyleResourceProvider {
    @Composable
    public fun textStyleForResource(resource: PilotTextStyleResource): TextStyle?
}

public class DefaultPilotTextStyleResourceProvider : PilotTextStyleResourceProvider {
    @Composable
    override fun textStyleForResource(resource: PilotTextStyleResource): TextStyle? =
        null
}

private class OverridingPilotTextStyleResourceProvider(
    vararg providers: PilotTextStyleResourceProvider,
) : PilotTextStyleResourceProvider {
    private val providers: List<PilotTextStyleResourceProvider> = listOf(*providers)
        .flatMap {
            if (it is OverridingPilotTextStyleResourceProvider) {
                it.providers
            } else {
                listOf(it)
            }
        }

    @Composable
    override fun textStyleForResource(resource: PilotTextStyleResource): TextStyle? =
        providers.firstNotNullOfOrNull { it.textStyleForResource(resource) }
}

public interface PilotImageResourceProvider {
    @Composable
    public fun painterForResource(resource: PilotImageResource): Painter?

    @Composable
    public fun requirePainterForResource(resource: PilotImageResource): Painter =
        painterForResource(resource) ?: throw IllegalArgumentException("Unsupported image resource: $resource")
}

public class DefaultPilotImageResourceProvider : PilotImageResourceProvider {
    @Composable
    override fun painterForResource(resource: PilotImageResource): Painter? =
        null
}

private class OverridingPilotImageResourceProvider(
    vararg providers: PilotImageResourceProvider,
) : PilotImageResourceProvider {
    private val providers: List<PilotImageResourceProvider> = listOf(*providers)
        .flatMap {
            if (it is OverridingPilotImageResourceProvider) {
                it.providers
            } else {
                listOf(it)
            }
        }

    @Composable
    override fun painterForResource(resource: PilotImageResource): Painter? =
        providers.firstNotNullOfOrNull { it.painterForResource(resource) }
}

public val LocalPilotTextStyleResourceProvider: ProvidableCompositionLocal<PilotTextStyleResourceProvider> = staticCompositionLocalOf { DefaultPilotTextStyleResourceProvider() }
public val LocalPilotImageResourceProvider: ProvidableCompositionLocal<PilotImageResourceProvider> = staticCompositionLocalOf { DefaultPilotImageResourceProvider() }

@Composable
public fun PilotResources(
    textStyleResourceProvider: PilotTextStyleResourceProvider = DefaultPilotTextStyleResourceProvider(),
    imageResourceProvider: PilotImageResourceProvider = DefaultPilotImageResourceProvider(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalPilotTextStyleResourceProvider provides OverridingPilotTextStyleResourceProvider(
            textStyleResourceProvider,
            LocalPilotTextStyleResourceProvider.current,
        ),
        LocalPilotImageResourceProvider provides OverridingPilotImageResourceProvider(
            imageResourceProvider,
            LocalPilotImageResourceProvider.current,
        ),
        content = content,
    )
}

@Composable
public fun pilotImageResourcePainter(imageResource: PilotImageResource): Painter =
    LocalPilotImageResourceProvider.current.requirePainterForResource(imageResource)
