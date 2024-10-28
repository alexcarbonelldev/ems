package com.ems.domain.common.base

import com.ems.coroutines.DispatcherProvider
import com.ems.domain.common.Either
import com.ems.domain.common.error.ErrorType
import com.ems.domain.common.error.HandledException

abstract class BaseUseCase<T> {

    abstract val dispatcherProvider: DispatcherProvider

    suspend fun handleResponse(function: suspend () -> T): Either<T> {
        return try {
            function().let { Either.Right(it) }
        } catch (e: HandledException) {
            Either.Left(e.type)
        } catch (e: Exception) {
            Either.Left(ErrorType.Default)
        }
    }
}
