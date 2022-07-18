package com.ems.ems.ui.screen.detail

import androidx.lifecycle.viewModelScope
import com.ems.domain.common.DispatcherProvider
import com.ems.domain.common.Either
import com.ems.domain.common.error.ErrorType
import com.ems.domain.session.model.HistoricalItemModel
import com.ems.domain.session.usecase.GetSessionHistoricalInfoUseCase
import com.ems.ems.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    override val dispatcherProvider: DispatcherProvider,
    private val getSessionHistoricalInfoUseCase: GetSessionHistoricalInfoUseCase
) : BaseViewModel<DetailViewState, DetailViewEffect, DetailViewIntent>(DetailViewState()) {

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
        updateState(viewState.value.copy(loading = true, error = false))

        viewModelScope.launch(dispatcherProvider.main) {
            when (val result = getSessionHistoricalInfoUseCase()) {
                is Either.Left -> handleHistoricalInfoError(result.type)
                is Either.Right -> handleHistoricalInfoSuccess(result.data)
            }
        }
    }

    private fun handleHistoricalInfoError(errorType: ErrorType) {
        when (errorType) {
            ErrorType.Network,
            ErrorType.Default -> updateState(viewState.value.copy(loading = false, error = true))
        }
    }

    private fun handleHistoricalInfoSuccess(historicalInfoList: List<HistoricalItemModel>) {
        updateState(
            viewState.value.copy(
                loading = false,
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
