package com.ems.ems.ui.screen.dashboard

import com.ems.ems.ui.common.base.ViewEffect

sealed class DashboardViewEffect : ViewEffect {

    object NavigateToDetail : DashboardViewEffect()
}
