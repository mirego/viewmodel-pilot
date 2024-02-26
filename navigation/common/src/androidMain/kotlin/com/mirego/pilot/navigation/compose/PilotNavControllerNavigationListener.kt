package com.mirego.pilot.navigation.compose

import androidx.navigation.NavController
import com.mirego.pilot.navigation.PilotNavigationListener
import com.mirego.pilot.navigation.PilotNavigationRoute

public open class PilotNavControllerNavigationListener<ROUTE : PilotNavigationRoute>(
    private val navController: NavController,
) : PilotNavigationListener<ROUTE>() {

    override fun push(route: ROUTE) {
        navController.navigate(route.navRoute)
    }

    override fun pop() {
        navController.popBackStack()
    }

    override fun popTo(route: ROUTE, inclusive: Boolean) {
        navController.popBackStack(
            route = route.navRoute,
            inclusive = inclusive,
        )
    }
}
