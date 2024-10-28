package com.ems.ems.ui.common.base

import androidx.lifecycle.ViewModel
import com.ems.coroutines.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<VS : ViewState, VE : ViewEffect, VI : ViewIntent>(initialViewState: VS) : ViewModel() {

    abstract val dispatcherProvider: DispatcherProvider

    private val _viewState = MutableStateFlow(initialViewState)
    val viewState: StateFlow<VS> get() = _viewState

    private val _viewEffects = MutableStateFlow<List<VE>>(emptyList())
    val viewEffects: StateFlow<List<VE>> get() = _viewEffects

    abstract fun onViewIntent(viewIntent: VI)

    protected fun updateState(state: VS) {
        _viewState.value = state
    }

    protected fun addEffect(effect: VE) {
        _viewEffects.value = viewEffects.value + effect
    }

    fun onEffectConsumed(effect: VE) {
        _viewEffects.value -= effect
    }
}
