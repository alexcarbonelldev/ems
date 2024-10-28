package com.ems.domain.common.error

sealed class ErrorType {
    data object Network : ErrorType()
    data object Default : ErrorType()
}
