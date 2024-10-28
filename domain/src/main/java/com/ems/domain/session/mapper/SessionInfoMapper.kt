package com.ems.domain.session.mapper

import com.ems.domain.common.extension.percentageOf
import com.ems.domain.session.model.HistoricalInfoItem
import com.ems.domain.session.model.LiveInfo
import com.ems.domain.session.model.SessionInfo
import com.ems.domain.session.model.SessionLiveInfo
import com.ems.domain.session.model.SessionQuasarInfo
import com.ems.domain.session.model.SessionStatisticsInfo
import javax.inject.Inject

class SessionInfoMapper @Inject constructor() {

    fun map(
        historicalInfo: List<HistoricalInfoItem>,
        liveInfo: LiveInfo
    ): SessionInfo {
        val quasarsActivePower = historicalInfo.map { it.quasarsActivePower }
        val energyDischargedFromQuasar = quasarsActivePower.filter { it < 0 }.sum()
        val energyChargedWithQuasar = quasarsActivePower.filter { it > 0 }.sum()

        return SessionInfo(
            quasarInfo = SessionQuasarInfo(
                dischargedEnergyFromQuasarKWh = energyDischargedFromQuasar / 60,
                chargedEnergyWithQuasarKWh = energyChargedWithQuasar / 60
            ),
            liveInfo = mapLiveInfo(liveInfo),
            statisticsInfo = mapStatisticsInfo(historicalInfo, energyDischargedFromQuasar)
        )
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
