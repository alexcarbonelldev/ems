package com.ems.data.session.datasource.network

import com.ems.data.session.repository.model.HistoricalItemDataModel
import com.ems.data.session.repository.model.LiveInfoDataModel

interface SessionRemoteDataSource {

    suspend fun getHistoricalInfo(): List<HistoricalItemDataModel>

    suspend fun getLiveInfo(): LiveInfoDataModel
}
