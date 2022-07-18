package com.ems.ems.ui.screen.detail

import com.ems.ems.ui.common.base.ViewIntent

sealed class DetailViewIntent : ViewIntent {

    object OnBackClick : DetailViewIntent()
    object OnRetryClick : DetailViewIntent()
}
