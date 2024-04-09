package com.mirego.pilot.navigation.compose

import androidx.navigation.NavController
import com.mirego.pilot.navigation.PilotNavigationListener
import com.mirego.pilot.navigation.PilotNavigationRoute

public open class PilotNavControllerNavigationListener<ROUTE : PilotNavigationRoute>(
    private val navController: NavController,
    private val rootName: String,
) : PilotNavigationListener<ROUTE>() {

    override fun push(route: ROUTE) {
        navController.navigate(route.navRoute)
    }

    override fun pop() {
        if (navController.currentBackStackEntry?.destination?.route != rootName) {
            navController.popBackStack()
        }
    }

    override fun popTo(route: ROUTE, inclusive: Boolean) {
        navController.popBackStack(
            route = route.navRoute,
            inclusive = inclusive,
        )
    }
}
