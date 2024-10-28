package com.ems.domain.session.usecase

import com.ems.coroutines.DispatcherProvider
import com.ems.domain.common.Either
import com.ems.domain.common.base.BaseUseCase
import com.ems.domain.session.model.HistoricalInfoItem
import com.ems.domain.session.repository.SessionRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSessionHistoricalInfoUseCase @Inject constructor(
    override val dispatcherProvider: DispatcherProvider,
    private val sessionRepository: SessionRepository
) : BaseUseCase<List<HistoricalInfoItem>>() {

    suspend operator fun invoke(): Either<List<HistoricalInfoItem>> = handleResponse {
        withContext(dispatcherProvider.default) {
            sessionRepository.getHistoricalInfo()
        }
    }
}
