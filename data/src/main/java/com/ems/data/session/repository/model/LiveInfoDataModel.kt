package com.ems.data.session.repository.model

data class LiveInfoDataModel(
    val solarPower: Double,
    val quasarsPower: Double,
    val gridPower: Double,
    val buildingDemand: Double,
    val systemSoc: Double,
    val totalEnergy: Double,
    val currentEnergy: Double
)
