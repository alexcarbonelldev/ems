package com.ems.data.session.common.error

import kotlinx.coroutines.CoroutineScope

interface ErrorHandler {

    suspend fun <T> handleError(block: suspend CoroutineScope.() -> T): T
}
