package com.ems.data.session.datasource.network.mapper

import com.ems.data.session.datasource.network.model.HistoricalItemNetworkModel
import com.ems.data.session.repository.model.HistoricalItemDataModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HistoricalItemNetworkModelMapper @Inject constructor() {

    fun toData(unmapped: HistoricalItemNetworkModel): HistoricalItemDataModel {
        return HistoricalItemDataModel(
            buildingActivePower = unmapped.buildingActivePower,
            gridActivePower = unmapped.gridActivePower,
            solarPanelActivePower = unmapped.solarPanelActivePower,
            quasarsActivePower = unmapped.quasarsActivePower,
            date = mapDate(unmapped.timestamp)
        )
    }

    private fun mapDate(date: String): Date {
        val df: DateFormat = SimpleDateFormat("yyyy-DD-dd'T'HH:mm:ssZZZZZ", Locale.getDefault())
        return df.parse(date) as Date
    }
}
