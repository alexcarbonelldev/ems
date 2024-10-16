package com.ems.data.session.repository.mapper

import com.ems.data.session.repository.model.HistoricalItemData
import com.ems.domain.session.model.HistoricalInfoItem
import javax.inject.Inject

class HistoricalItemDataMapper @Inject constructor() {

    fun toDomain(unmapped: HistoricalItemData): HistoricalInfoItem {
        return HistoricalInfoItem(
            buildingActivePower = unmapped.buildingActivePower,
            gridActivePower = unmapped.gridActivePower,
            solarPanelActivePower = unmapped.solarPanelActivePower,
            quasarsActivePower = unmapped.quasarsActivePower,
            date = unmapped.date
        )
    }
}
