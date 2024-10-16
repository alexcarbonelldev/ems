package com.ems.data.session.datasource.network.mapper

import com.ems.data.session.datasource.network.model.HistoricalItemNetwork
import com.ems.data.session.repository.model.HistoricalItemData
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


class HistoricalItemNetworkMapper @Inject constructor() {

    fun toData(unmapped: HistoricalItemNetwork): HistoricalItemData {
        return HistoricalItemData(
            buildingActivePower = unmapped.buildingActivePower,
            gridActivePower = unmapped.gridActivePower,
            solarPanelActivePower = unmapped.solarPanelActivePower,
            quasarsActivePower = unmapped.quasarsActivePower,
            date = mapDate(unmapped.timestamp)
        )
    }

    private fun mapDate(date: String): Date {
        val df: DateFormat = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
        return df.parse(date) as Date
    }

    private companion object {
        const val DATE_PATTERN = "yyyy-DD-dd'T'HH:mm:ssZZZZZ"
    }
}
