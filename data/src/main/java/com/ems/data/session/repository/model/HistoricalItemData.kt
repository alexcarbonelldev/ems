package com.ems.data.session.repository.model

import java.util.*

data class HistoricalItemData(
    val buildingActivePower: Double,
    val gridActivePower: Double,
    val solarPanelActivePower: Double,
    val quasarsActivePower: Double,
    val date: Date
)
