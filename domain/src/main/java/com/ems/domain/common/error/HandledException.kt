package com.ems.domain.common.error

data class HandledException(val type: ErrorType) : Exception(type.toString())
