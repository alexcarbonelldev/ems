package com.ems.data.session.repository.mapper

import com.ems.data.session.repository.model.HistoricalItemDataModel
import com.ems.domain.session.model.HistoricalItemModel
import javax.inject.Inject

class HistoricalItemDataModelMapper @Inject constructor() {

    fun toDomain(unmapped: HistoricalItemDataModel): HistoricalItemModel {
        return HistoricalItemModel(
            buildingActivePower = unmapped.buildingActivePower,
            gridActivePower = unmapped.gridActivePower,
            solarPanelActivePower = unmapped.solarPanelActivePower,
            quasarsActivePower = unmapped.quasarsActivePower,
            date = unmapped.date
        )
    }
}
