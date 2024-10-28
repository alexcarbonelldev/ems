package com.ems.domain.session.usecase

import com.ems.coroutines.DispatcherProvider
import com.ems.domain.common.Either
import com.ems.domain.common.base.BaseUseCase
import com.ems.domain.session.mapper.SessionInfoMapper
import com.ems.domain.session.model.SessionInfo
import com.ems.domain.session.repository.SessionRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSessionInfoUseCase @Inject constructor(
    override val dispatcherProvider: DispatcherProvider,
    private val sessionRepository: SessionRepository,
    private val sessionInfoMapper: SessionInfoMapper
) : BaseUseCase<SessionInfo>() {

    suspend operator fun invoke(): Either<SessionInfo> = handleResponse {
        withContext(dispatcherProvider.default) {
            val historicalInfoAsync = async { sessionRepository.getHistoricalInfo() }
            val liveInfoAsync = async { sessionRepository.getLiveInfo() }
            val historicalInfo = historicalInfoAsync.await()
            val liveInfo = liveInfoAsync.await()

            sessionInfoMapper.map(historicalInfo, liveInfo)
        }
    }
}
