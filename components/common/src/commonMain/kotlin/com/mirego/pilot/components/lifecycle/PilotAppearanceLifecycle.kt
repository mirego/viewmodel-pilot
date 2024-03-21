package com.mirego.pilot.components.lifecycle

import kotlinx.coroutines.CoroutineScope

public interface PilotAppearanceLifecycle {

    public var firstOnAppear: Boolean
    public var viewAppearanceScope: CoroutineScope?

    public fun onAppear(coroutineScope: CoroutineScope)

    public fun onAppearFirst()

    public fun onAppearSubsequent(coroutineScope: CoroutineScope)

    public fun onDisappear()
}
