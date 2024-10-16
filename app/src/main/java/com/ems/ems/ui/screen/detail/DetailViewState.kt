package com.ems.ems.ui.screen.detail

import androidx.compose.runtime.Immutable
import com.ems.ems.ui.common.base.ViewState
import java.util.Date

sealed interface DetailViewState : ViewState {
    object Loading : DetailViewState
    object Error : DetailViewState

    @Immutable
    data class Data(
        val chartValues: List<ChartValue> = emptyList()
    ) : DetailViewState
}

@Immutable
data class ChartValue(
    val solarPanelValue: Double,
    val gridValue: Double,
    val quasarValue: Double,
    val date: Date
)
