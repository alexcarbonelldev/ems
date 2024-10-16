package com.ems.ems.ui.common.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun <VS : ViewState, VE : ViewEffect, VI : ViewIntent> BaseViewModel<VS, VE, VI>.ViewEffectObserver(
    onEffect: (VE) -> Unit
) {
    LaunchedEffect(Unit) {
        this@ViewEffectObserver.viewEffects.collect { effects ->
            if (effects.isEmpty()) return@collect
            val effect = effects.first()
            onEffect(effect)
            this@ViewEffectObserver.onEffectConsumed(effect)
        }
    }
}
