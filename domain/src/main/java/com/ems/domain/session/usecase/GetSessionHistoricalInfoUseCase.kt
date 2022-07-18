package com.ems.domain.session.usecase

import com.ems.domain.common.DispatcherProvider
import com.ems.domain.common.Either
import com.ems.domain.common.base.BaseUseCase
import com.ems.domain.session.model.HistoricalItemModel
import com.ems.domain.session.repository.SessionRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSessionHistoricalInfoUseCase @Inject constructor(
    override val dispatcherProvider: DispatcherProvider,
    private val sessionRepository: SessionRepository
) : BaseUseCase<List<HistoricalItemModel>>() {

    suspend operator fun invoke(): Either<List<HistoricalItemModel>> = handleResponse {
        withContext(dispatcherProvider.default) {
            sessionRepository.getHistoricalInfo()
        }
    }
}
