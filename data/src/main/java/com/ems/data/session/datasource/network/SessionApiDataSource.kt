package com.ems.data.session.datasource.network

import com.ems.data.session.common.error.ApiErrorHandler
import com.ems.data.session.datasource.network.mapper.HistoricalItemNetworkModelMapper
import com.ems.data.session.datasource.network.mapper.LiveInfoNetworkModelMapper
import com.ems.data.session.datasource.network.model.HistoricalItemNetworkModel
import com.ems.data.session.datasource.network.model.LiveInfoNetworkModel
import com.ems.data.session.repository.model.HistoricalItemDataModel
import com.ems.data.session.repository.model.LiveInfoDataModel
import com.ems.domain.common.DispatcherProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SessionApiDataSource @Inject constructor(
    private val historicalItemNetworkModelMapper: HistoricalItemNetworkModelMapper,
    private val liveInfoNetworkModelMapper: LiveInfoNetworkModelMapper,
    private val dispatcherProvider: DispatcherProvider
) : SessionRemoteDataSource, ApiErrorHandler {

    private val gson by lazy { Gson() }

    override suspend fun getHistoricalInfo(): List<HistoricalItemDataModel> = handleError {
        withContext(dispatcherProvider.io) {
            delay(1000)

            throwExceptionRandomly()

            gson.fromJson<List<HistoricalItemNetworkModel>>(
                JsonMock.getHistoricalInfoJson(),
                object : TypeToken<List<HistoricalItemNetworkModel?>?>() {}.type
            ).map { historicalItemNetworkModelMapper.toData(it) }
        }
    }

    override suspend fun getLiveInfo(): LiveInfoDataModel = handleError {
        withContext(dispatcherProvider.io) {

            delay(1000)

            throwExceptionRandomly()

            gson.fromJson(JsonMock.getLiveInfoJson(), LiveInfoNetworkModel::class.java)
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
