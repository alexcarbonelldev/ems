package com.ems.ems.ui.screen.detail

import com.ems.ems.ui.common.base.ViewEffect

sealed class DetailViewEffect : ViewEffect {

    object NavigateBack : DetailViewEffect()
}
