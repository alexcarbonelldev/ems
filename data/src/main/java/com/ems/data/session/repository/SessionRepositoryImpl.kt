package com.ems.data.session.repository

import com.ems.data.session.datasource.network.SessionRemoteDataSource
import com.ems.data.session.repository.mapper.HistoricalItemDataModelMapper
import com.ems.data.session.repository.mapper.LiveInfoDataModelMapper
import com.ems.domain.session.model.HistoricalItemModel
import com.ems.domain.session.model.LiveInfoModel
import com.ems.domain.session.repository.SessionRepository
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val energyNetworkDataSource: SessionRemoteDataSource,
    private val historicalItemDataModelMapper: HistoricalItemDataModelMapper,
    private val liveInfoDataModelMapper: LiveInfoDataModelMapper
) : SessionRepository {

    override suspend fun getHistoricalInfo(): List<HistoricalItemModel> {
        return energyNetworkDataSource.getHistoricalInfo().map { historicalInfo ->
            historicalItemDataModelMapper.toDomain(historicalInfo)
        }
    }

    override suspend fun getLiveInfo(): LiveInfoModel {
        return energyNetworkDataSource.getLiveInfo().let { liveInfo ->
            liveInfoDataModelMapper.toDomain(liveInfo)
        }
    }
}
