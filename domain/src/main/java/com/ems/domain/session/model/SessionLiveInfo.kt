package com.ems.domain.session.model

data class SessionLiveInfo(
    val solarPower: Double,
    val quasarsPower: Double,
    val gridPower: Double,
    val buildingDemand: Double
)
