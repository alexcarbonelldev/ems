package com.ems.domain.session.repository

import com.ems.domain.session.model.HistoricalInfoItem
import com.ems.domain.session.model.LiveInfo

interface SessionRepository {

    suspend fun getHistoricalInfo(): List<HistoricalInfoItem>

    suspend fun getLiveInfo(): LiveInfo
}
