package com.ems.data.session.datasource.network

import com.ems.data.session.common.error.ApiErrorHandler
import com.ems.data.session.datasource.network.mapper.HistoricalItemNetworkMapper
import com.ems.data.session.datasource.network.mapper.LiveInfoNetworkModelMapper
import com.ems.data.session.datasource.network.model.HistoricalItemNetwork
import com.ems.data.session.datasource.network.model.LiveInfoNetwork
import com.ems.data.session.repository.model.HistoricalItemData
import com.ems.data.session.repository.model.LiveInfoData
import com.ems.domain.common.DispatcherProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SessionApiDataSource @Inject constructor(
    private val historicalItemNetworkMapper: HistoricalItemNetworkMapper,
    private val liveInfoNetworkModelMapper: LiveInfoNetworkModelMapper,
    private val dispatcherProvider: DispatcherProvider
) : SessionRemoteDataSource, ApiErrorHandler {

    private val gson by lazy { Gson() }

    override suspend fun getHistoricalInfo(): List<HistoricalItemData> = handleError {
        withContext(dispatcherProvider.io) {
            delay(1000)

            throwExceptionRandomly()

            gson.fromJson<List<HistoricalItemNetwork>>(
                JsonMock.getHistoricalInfoJson(),
                object : TypeToken<List<HistoricalItemNetwork?>?>() {}.type
            ).map { historicalItemNetworkMapper.toData(it) }
        }
    }

    override suspend fun getLiveInfo(): LiveInfoData = handleError {
        withContext(dispatcherProvider.io) {

            delay(1000)

            throwExceptionRandomly()

            gson.fromJson(JsonMock.getLiveInfoJson(), LiveInfoNetwork::class.java)
                .let { liveInfoNetworkModelMapper.toData(it) }
        }
    }

    private fun throwExceptionRandomly() {
        val randomNumber = (0..10).random()
        when {
            randomNumber > 5 -> throw Exception()
            else -> Unit
        }
    }
}
