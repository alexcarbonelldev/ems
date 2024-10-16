package com.ems.data.session.repository

import com.ems.data.session.datasource.network.SessionRemoteDataSource
import com.ems.data.session.repository.mapper.HistoricalItemDataMapper
import com.ems.data.session.repository.mapper.LiveInfoDataMapper
import com.ems.domain.session.model.HistoricalInfoItem
import com.ems.domain.session.model.LiveInfo
import com.ems.domain.session.repository.SessionRepository
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val energyNetworkDataSource: SessionRemoteDataSource,
    private val historicalItemDataMapper: HistoricalItemDataMapper,
    private val liveInfoDataMapper: LiveInfoDataMapper
) : SessionRepository {

    override suspend fun getHistoricalInfo(): List<HistoricalInfoItem> {
        return energyNetworkDataSource.getHistoricalInfo().map { historicalInfo ->
            historicalItemDataMapper.toDomain(historicalInfo)
        }
    }

    override suspend fun getLiveInfo(): LiveInfo {
        return energyNetworkDataSource.getLiveInfo().let { liveInfo ->
            liveInfoDataMapper.toDomain(liveInfo)
        }
    }
}
