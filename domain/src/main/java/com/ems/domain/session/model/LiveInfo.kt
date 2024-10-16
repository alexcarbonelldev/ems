package com.ems.domain.session.model

data class LiveInfo(
    val solarPower: Double,
    val quasarsPower: Double,
    val gridPower: Double,
    val buildingDemand: Double,
    val systemSoc: Double,
    val totalEnergy: Double,
    val currentEnergy: Double
)
