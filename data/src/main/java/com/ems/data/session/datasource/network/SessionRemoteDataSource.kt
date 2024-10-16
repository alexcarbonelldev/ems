package com.ems.data.session.datasource.network

import com.ems.data.session.repository.model.HistoricalItemData
import com.ems.data.session.repository.model.LiveInfoData

interface SessionRemoteDataSource {

    suspend fun getHistoricalInfo(): List<HistoricalItemData>

    suspend fun getLiveInfo(): LiveInfoData
}
