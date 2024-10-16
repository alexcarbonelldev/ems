package com.ems.data.session.datasource.network.mapper

import com.ems.data.session.datasource.network.model.LiveInfoNetwork
import com.ems.data.session.repository.model.LiveInfoData
import javax.inject.Inject

class LiveInfoNetworkModelMapper @Inject constructor() {

    fun toData(unmapped: LiveInfoNetwork): LiveInfoData {
        return LiveInfoData(
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
