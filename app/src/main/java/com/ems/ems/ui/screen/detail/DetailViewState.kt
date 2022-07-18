package com.ems.ems.ui.screen.detail

import androidx.compose.runtime.Immutable
import com.ems.ems.ui.common.base.ViewState
import java.util.*

@Immutable
data class DetailViewState(
    val loading: Boolean = true,
    val error: Boolean = false,
    val chartValues: List<ChartValue> = emptyList()
) : ViewState

@Immutable
data class ChartValue(
    val solarPanelValue: Double,
    val gridValue: Double,
    val quasarValue: Double,
    val date: Date
)
