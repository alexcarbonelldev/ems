package com.ems.domain.common

import com.ems.domain.common.error.ErrorType

sealed class Either<T> {

    data class Left<T>(val type: ErrorType) : Either<T>()
    data class Right<T>(val data: T) : Either<T>()
}
