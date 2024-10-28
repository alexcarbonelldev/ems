package com.ems.ems.ui.screen.dashboard

import androidx.lifecycle.viewModelScope
import com.ems.coroutines.DispatcherProvider
import com.ems.domain.common.Either
import com.ems.domain.session.model.SessionInfo
import com.ems.domain.session.usecase.GetSessionInfoUseCase
import com.ems.ems.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    override val dispatcherProvider: DispatcherProvider,
    private val getSessionInfoUseCase: GetSessionInfoUseCase
) : BaseViewModel<DashboardViewState, DashboardViewEffect, DashboardViewIntent>(DashboardViewState.Loading) {

    init {
        getSessionInfo()
    }

    override fun onViewIntent(viewIntent: DashboardViewIntent) {
        when (viewIntent) {
            DashboardViewIntent.OnStatisticsClick -> addEffect(DashboardViewEffect.NavigateToDetail)
            DashboardViewIntent.OnRetryClick -> getSessionInfo()
        }
    }

    private fun getSessionInfo() {
        updateState(DashboardViewState.Loading)

        viewModelScope.launch(dispatcherProvider.main) {
            when (val result = getSessionInfoUseCase()) {
                is Either.Left -> updateState(DashboardViewState.Error)
                is Either.Right -> handleSessionInfoSuccess(result.data)
            }
        }
    }

    private fun handleSessionInfoSuccess(sessionInfo: SessionInfo) {
        updateState(
            DashboardViewState.Data(
                dischargedEnergyFromQuasar = sessionInfo.quasarInfo.dischargedEnergyFromQuasarKWh,
                chargedEnergyWithQuasar = sessionInfo.quasarInfo.chargedEnergyWithQuasarKWh,
                liveInfoViewState = mapSessionInfoToLiveInfoViewState(sessionInfo),
                statisticsInfoViewState = mapSessionInfoToStatisticsInfoViewState(sessionInfo)
            )
        )
    }

    private fun mapSessionInfoToStatisticsInfoViewState(dashboardInfo: SessionInfo): StatisticsInfoViewState {
        return StatisticsInfoViewState(
            solarPowerPercentage = dashboardInfo.statisticsInfo.solarPowerPercentage,
            quasarsPowerPercentage = dashboardInfo.statisticsInfo.quasarsPowerPercentage,
            gridPowerPercentage = dashboardInfo.statisticsInfo.gridPowerPercentage
        )
    }

    private fun mapSessionInfoToLiveInfoViewState(dashboardInfo: SessionInfo): LiveInfoViewState {
        return LiveInfoViewState(
            solarPower = dashboardInfo.liveInfo.solarPower,
            quasarsPower = dashboardInfo.liveInfo.quasarsPower,
            gridPower = dashboardInfo.liveInfo.gridPower,
            buildingDemand = dashboardInfo.liveInfo.buildingDemand
        )
    }
}
