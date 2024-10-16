package com.ems.data.session.datasource.network.model

import com.google.gson.annotations.SerializedName

data class HistoricalItemNetwork(
    @SerializedName("building_active_power")
    val buildingActivePower: Double,
    @SerializedName("grid_active_power")
    val gridActivePower: Double,
    @SerializedName("pv_active_power")
    val solarPanelActivePower: Double,
    @SerializedName("quasars_active_power")
    val quasarsActivePower: Double,
    @SerializedName("timestamp")
    val timestamp: String
)
