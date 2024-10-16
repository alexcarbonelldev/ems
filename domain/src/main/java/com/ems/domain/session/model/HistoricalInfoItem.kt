package com.ems.domain.session.model

import java.util.*

data class HistoricalInfoItem(
    val buildingActivePower: Double,
    val gridActivePower: Double,
    val solarPanelActivePower: Double,
    val quasarsActivePower: Double,
    val date: Date
)
