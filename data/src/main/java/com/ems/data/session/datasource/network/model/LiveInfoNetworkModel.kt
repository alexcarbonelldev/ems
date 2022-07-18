package com.ems.data.session.datasource.network.model

import com.google.gson.annotations.SerializedName

data class LiveInfoNetworkModel(
    @SerializedName("solar_power")
    val solarPower: Double,
    @SerializedName("quasars_power")
    val quasarsPower: Double,
    @SerializedName("grid_power")
    val gridPower: Double,
    @SerializedName("building_demand")
    val buildingDemand: Double,
    @SerializedName("system_soc")
    val systemSoc: Double,
    @SerializedName("total_energy")
    val totalEnergy: Double,
    @SerializedName("current_energy")
    val currentEnergy: Double
)
