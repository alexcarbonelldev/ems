package com.ems.ems.ui.screen.dashboard

import androidx.compose.runtime.Immutable
import com.ems.ems.ui.common.base.ViewState


sealed interface DashboardViewState : ViewState {
    data object Loading : DashboardViewState
    data object Error : DashboardViewState

    @Immutable
    data class Data(
        val dischargedEnergyFromQuasar: Double = 0.0,
        val chargedEnergyWithQuasar: Double = 0.0,
        val liveInfoViewState: LiveInfoViewState = LiveInfoViewState(),
        val statisticsInfoViewState: StatisticsInfoViewState = StatisticsInfoViewState()
    ) : DashboardViewState
}

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
