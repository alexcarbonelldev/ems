package com.ems.ems.ui.screen.detail

import androidx.lifecycle.viewModelScope
import com.ems.coroutines.DispatcherProvider
import com.ems.domain.common.Either
import com.ems.domain.session.model.HistoricalInfoItem
import com.ems.domain.session.usecase.GetSessionHistoricalInfoUseCase
import com.ems.ems.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    override val dispatcherProvider: DispatcherProvider,
    private val getSessionHistoricalInfoUseCase: GetSessionHistoricalInfoUseCase
) : BaseViewModel<DetailViewState, DetailViewEffect, DetailViewIntent>(DetailViewState.Loading) {

    init {
        getSessionHistoricalInfo()
    }

    override fun onViewIntent(viewIntent: DetailViewIntent) {
        when (viewIntent) {
            DetailViewIntent.OnBackClick -> addEffect(DetailViewEffect.NavigateBack)
            DetailViewIntent.OnRetryClick -> getSessionHistoricalInfo()
        }
    }

    private fun getSessionHistoricalInfo() {
        updateState(DetailViewState.Loading)

        viewModelScope.launch(dispatcherProvider.main) {
            when (val result = getSessionHistoricalInfoUseCase()) {
                is Either.Left -> updateState(DetailViewState.Error)
                is Either.Right -> handleHistoricalInfoSuccess(result.data)
            }
        }
    }

    private fun handleHistoricalInfoSuccess(historicalInfoList: List<HistoricalInfoItem>) {
        updateState(
            DetailViewState.Data(
                chartValues = historicalInfoList.map { historicalInfo ->
                    ChartValue(
                        solarPanelValue = historicalInfo.solarPanelActivePower,
                        gridValue = historicalInfo.gridActivePower,
                        quasarValue = historicalInfo.quasarsActivePower * -1,
                        historicalInfo.date
                    )
                }
            )
        )
    }
}
