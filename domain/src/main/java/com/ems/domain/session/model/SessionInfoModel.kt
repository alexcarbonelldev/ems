package com.ems.domain.session.model

data class SessionInfoModel(
    val quasarInfo: SessionQuasarInfo,
    val liveInfo: SessionLiveInfoModel,
    val statisticsInfo: SessionStatisticsInfoModel
)
