package com.ems.domain.session.repository

import com.ems.domain.session.model.HistoricalItemModel
import com.ems.domain.session.model.LiveInfoModel

interface SessionRepository {

    suspend fun getHistoricalInfo(): List<HistoricalItemModel>

    suspend fun getLiveInfo(): LiveInfoModel
}
