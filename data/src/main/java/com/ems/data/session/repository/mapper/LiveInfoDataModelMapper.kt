package com.ems.data.session.repository.mapper

import com.ems.data.session.repository.model.LiveInfoDataModel
import com.ems.domain.session.model.LiveInfoModel
import javax.inject.Inject

class LiveInfoDataModelMapper @Inject constructor() {

    fun toDomain(unmapped: LiveInfoDataModel): LiveInfoModel {
        return LiveInfoModel(
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
