package com.ems.data.session.common.error

import android.util.Log
import com.ems.domain.common.error.ErrorType
import com.ems.domain.common.error.HandledException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

interface ApiErrorHandler : ErrorHandler {

    override suspend fun <T> handleError(block: suspend CoroutineScope.() -> T): T {
        return coroutineScope {
            try {
                block()
            } catch (e: Exception) {
                Log.e("NetworkErrorHandler", "Network error: ${e.localizedMessage}")
                throw HandledException(ErrorType.Network)
            }
        }
    }
}
