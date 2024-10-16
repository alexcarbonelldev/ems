package com.ems.domain.session.usecase

import com.ems.domain.common.DispatcherProvider
import com.ems.domain.common.Either
import com.ems.domain.common.base.BaseUseCase
import com.ems.domain.common.extension.percentageOf
import com.ems.domain.session.model.HistoricalInfoItem
import com.ems.domain.session.model.LiveInfo
import com.ems.domain.session.model.SessionInfo
import com.ems.domain.session.model.SessionLiveInfo
import com.ems.domain.session.model.SessionQuasarInfo
import com.ems.domain.session.model.SessionStatisticsInfo
import com.ems.domain.session.repository.SessionRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSessionInfoUseCase @Inject constructor(
    override val dispatcherProvider: DispatcherProvider,
    private val sessionRepository: SessionRepository
) : BaseUseCase<SessionInfo>() {

    suspend operator fun invoke(): Either<SessionInfo> = handleResponse {
        withContext(dispatcherProvider.default) {
            val historicalInfoAsync = async { sessionRepository.getHistoricalInfo() }
            val liveInfoAsync = async { sessionRepository.getLiveInfo() }
            val historicalInfo = historicalInfoAsync.await()
            val liveInfo = liveInfoAsync.await()

            val quasarsActivePower = historicalInfo.map { it.quasarsActivePower }
            val energyDischargedFromQuasar = quasarsActivePower
                .filter { it < 0 }
                .sum()
            val energyChargedWithQuasar = quasarsActivePower
                .filter { it > 0 }
                .sum()

            SessionInfo(
                quasarInfo = SessionQuasarInfo(
                    dischargedEnergyFromQuasarKWh = energyDischargedFromQuasar / 60,
                    chargedEnergyWithQuasarKWh = energyChargedWithQuasar / 60
                ),
                liveInfo = mapLiveInfo(liveInfo),
                statisticsInfo = mapStatisticsInfo(historicalInfo, energyDischargedFromQuasar)
            )
        }
    }

    private fun mapStatisticsInfo(
        historicalInfo: List<HistoricalInfoItem>,
        energyDischargedFromQuasar: Double,
    ): SessionStatisticsInfo {
        val energyFromGrid = historicalInfo.sumOf { it.gridActivePower }
        val energyFromSolarPanel = historicalInfo.sumOf { it.solarPanelActivePower }
        val buildingEnergyDemand = historicalInfo.sumOf { it.buildingActivePower }

        return SessionStatisticsInfo(
            solarPowerPercentage = energyFromSolarPanel.percentageOf(buildingEnergyDemand),
            quasarsPowerPercentage = energyDischargedFromQuasar.percentageOf(buildingEnergyDemand),
            gridPowerPercentage = energyFromGrid.percentageOf(buildingEnergyDemand)
        )
    }

    private fun mapLiveInfo(liveInfo: LiveInfo): SessionLiveInfo {
        return SessionLiveInfo(
            solarPower = liveInfo.solarPower,
            quasarsPower = liveInfo.quasarsPower,
            gridPower = liveInfo.gridPower,
            buildingDemand = liveInfo.buildingDemand
        )
    }
}
