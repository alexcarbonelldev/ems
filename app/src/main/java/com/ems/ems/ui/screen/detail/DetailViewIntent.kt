package com.ems.ems.ui.screen.detail

import com.ems.ems.ui.common.base.ViewIntent

sealed class DetailViewIntent : ViewIntent {

    data object OnBackClick : DetailViewIntent()
    data object OnRetryClick : DetailViewIntent()
}
