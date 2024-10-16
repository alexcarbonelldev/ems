package com.ems.domain.session.model

data class SessionInfo(
    val quasarInfo: SessionQuasarInfo,
    val liveInfo: SessionLiveInfo,
    val statisticsInfo: SessionStatisticsInfo
)
