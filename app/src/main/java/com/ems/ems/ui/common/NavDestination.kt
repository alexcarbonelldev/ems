package com.ems.ems.ui.common

sealed class NavDestination(val route: String) {

    data object DashboardScreen : NavDestination("dashboard")
    data object DetailScreen : NavDestination("detail")
}
