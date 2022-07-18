package com.ems.ems.ui.common

sealed class NavDestination(val route: String) {

    object DashboardScreen : NavDestination("dashboard")
    object DetailScreen : NavDestination("detail")
}
