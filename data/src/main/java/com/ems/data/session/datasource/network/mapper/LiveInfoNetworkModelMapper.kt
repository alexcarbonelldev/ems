package com.ems.data.session.datasource.network.mapper

import com.ems.data.session.datasource.network.model.LiveInfoNetworkModel
import com.ems.data.session.repository.model.LiveInfoDataModel
import javax.inject.Inject

class LiveInfoNetworkModelMapper @Inject constructor() {

    fun toData(unmapped: LiveInfoNetworkModel): LiveInfoDataModel {
        return LiveInfoDataModel(
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
