package com.mirego.pilot.components.lifecycle

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

public open class PilotAppearanceLifecycleImpl : PilotAppearanceLifecycle {

    override var firstOnAppear: Boolean = true
    override var viewAppearanceScope: CoroutineScope? = null

    public override fun onAppear(coroutineScope: CoroutineScope) {}

    public override fun onAppearFirst() {}

    public override fun onAppearSubsequent(coroutineScope: CoroutineScope) {}

    public override fun onDisappear() {
        cancelViewAppearanceScope()
    }

    private fun cancelViewAppearanceScope() {
        viewAppearanceScope?.cancel()
        viewAppearanceScope = null
    }
}

public fun PilotAppearanceLifecycle.onAppear() {
    viewAppearanceScope?.cancel()
    viewAppearanceScope = MainScope().also {
        onAppear(it)
        if (firstOnAppear) {
            firstOnAppear = false
            onAppearFirst()
        } else {
            onAppearSubsequent(it)
        }
    }
}
