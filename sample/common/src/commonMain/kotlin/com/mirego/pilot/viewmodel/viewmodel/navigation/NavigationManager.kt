package com.mirego.pilot.viewmodel.viewmodel.navigation

import com.mirego.pilot.navigation.DefaultPilotNavigationManager
import kotlinx.coroutines.CoroutineScope

public class NavigationManager(
    coroutineScope: CoroutineScope
): DefaultPilotNavigationManager<NavigationRoute, NavigationAction>(coroutineScope) {
}