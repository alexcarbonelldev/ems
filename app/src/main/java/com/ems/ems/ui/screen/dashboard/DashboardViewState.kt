package com.ems.ems.ui.screen.dashboard

import androidx.compose.runtime.Immutable
import com.ems.ems.ui.common.base.ViewState

@Immutable
data class DashboardViewState(
    val loading: Boolean = true,
    val error: Boolean = false,
    val dischargedEnergyFromQuasar: Double = 0.0,
    val chargedEnergyWithQuasar: Double = 0.0,
    val liveInfoViewState: LiveInfoViewState = LiveInfoViewState(),
    val statisticsInfoViewState: StatisticsInfoViewState = StatisticsInfoViewState()
) : ViewState

@Immutable
data class LiveInfoViewState(
    val solarPower: Double = 0.0,
    val quasarsPower: Double = 0.0,
    val gridPower: Double = 0.0,
    val buildingDemand: Double = 0.0
)

@Immutable
data class StatisticsInfoViewState(
    val solarPowerPercentage: Double = 0.0,
    val quasarsPowerPercentage: Double = 0.0,
    val gridPowerPercentage: Double = 0.0
)
