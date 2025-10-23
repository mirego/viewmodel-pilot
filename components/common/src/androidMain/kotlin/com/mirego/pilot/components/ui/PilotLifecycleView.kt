package com.mirego.pilot.components.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.mirego.pilot.components.lifecycle.PilotAppearanceLifecycle
import com.mirego.pilot.components.lifecycle.onAppear

@Composable
public fun PilotLifecycleView(appearanceLifecycle: PilotAppearanceLifecycle, content: @Composable () -> Unit) {
    val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
    content()
    DisposableEffect(appearanceLifecycle, lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                appearanceLifecycle.onAppear()
            } else if (event == Lifecycle.Event.ON_STOP) {
                appearanceLifecycle.onDisappear()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                appearanceLifecycle.onDisappear()
            }
        }
    }
}
