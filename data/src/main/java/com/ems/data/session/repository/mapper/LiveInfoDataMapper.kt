package com.ems.data.session.repository.mapper

import com.ems.data.session.repository.model.LiveInfoData
import com.ems.domain.session.model.LiveInfo
import javax.inject.Inject

class LiveInfoDataMapper @Inject constructor() {

    fun toDomain(unmapped: LiveInfoData): LiveInfo {
        return LiveInfo(
            solarPower = unmapped.solarPower,
            quasarsPower = unmapped.quasarsPower,
            gridPower = unmapped.gridPower,
            buildingDemand = unmapped.buildingDemand,
            systemSoc = unmapped.systemSoc,
            totalEnergy = unmapped.totalEnergy,
            currentEnergy = unmapped.currentEnergy
        )
    }
}
