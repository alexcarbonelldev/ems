package com.ems.domain.session.model

import java.util.*

data class HistoricalItemModel(
    val buildingActivePower: Double,
    val gridActivePower: Double,
    val solarPanelActivePower: Double,
    val quasarsActivePower: Double,
    val date: Date
)
