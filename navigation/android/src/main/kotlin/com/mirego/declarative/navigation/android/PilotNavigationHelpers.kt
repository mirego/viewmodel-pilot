package com.mirego.declarative.navigation.android

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.mirego.declarative.navigation.PilotNavigationManager
import com.mirego.declarative.navigation.PilotNavigationRoute

private const val UNIQUE_ID_PARAM = "uniqueId"

internal val PilotNavigationRoute.navRoute: String
    get() = "$name/$uniqueId"

public fun pilotNavRoute(name: String): String =
    "$name/{$UNIQUE_ID_PARAM}"

public val pilotNavArguments: List<NamedNavArgument> = listOf(navArgument(UNIQUE_ID_PARAM) { type = NavType.StringType })

public fun <ROUTE : PilotNavigationRoute, ACTION : Any, T : ROUTE> PilotNavigationManager<ROUTE, ACTION>.findRoute(backStackEntry: NavBackStackEntry): T {
    val uniqueId = backStackEntry.arguments?.getString(UNIQUE_ID_PARAM)
        ?: throw IllegalStateException("Unique ID not found in backStackEntry $backStackEntry")
    return (findRoute(uniqueId) as? T)
        ?: throw IllegalStateException("Cannot find route with id $uniqueId")
}

public inline fun <reified T : Enum<T>> findRouteName(backStackEntry: NavBackStackEntry): T? =
    backStackEntry.destination.route?.let { route ->
        val routeName = route.split("/").firstOrNull()
        enumValues<T>().find { it.name == routeName }
    }
