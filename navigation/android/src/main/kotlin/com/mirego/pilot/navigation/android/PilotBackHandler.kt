package com.mirego.pilot.navigation.android

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.mirego.pilot.navigation.PilotNavigationManager

@Composable
public fun PilotBackHandler(navController: NavHostController, navigationManager: PilotNavigationManager<*, *>, rootName: String) {
    val backStackEntry by navController.currentBackStackEntryFlow.collectAsState(initial = null)
    BackHandler(enabled = backStackEntry?.destination?.route != rootName) {
        navigationManager.pop()
    }
}
