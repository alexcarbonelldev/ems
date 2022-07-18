package com.ems.ems.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ems.ems.ui.common.NavDestination
import com.ems.ems.ui.screen.dashboard.DashboardScreen
import com.ems.ems.ui.screen.detail.DetailScreen
import com.ems.ems.ui.theme.EmsTheme

@Composable
fun EmsApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavDestination.DashboardScreen.route
    ) {
        composable(NavDestination.DashboardScreen.route) {
            DashboardScreen(navController)
        }
        composable(NavDestination.DetailScreen.route) {
            DetailScreen(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EmsTheme {
        EmsApp()
    }
}
