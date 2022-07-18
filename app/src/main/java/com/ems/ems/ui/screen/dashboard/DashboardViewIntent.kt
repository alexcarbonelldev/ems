package com.ems.ems.ui.screen.dashboard

import com.ems.ems.ui.common.base.ViewIntent

sealed class DashboardViewIntent : ViewIntent {

    object OnStatisticsClick : DashboardViewIntent()
    object OnRetryClick : DashboardViewIntent()
}
