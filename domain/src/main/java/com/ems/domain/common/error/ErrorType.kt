package com.ems.domain.common.error

sealed class ErrorType {
    object Network : ErrorType()
    object Default : ErrorType()
}
