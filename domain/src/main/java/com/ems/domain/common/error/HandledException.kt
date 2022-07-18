package com.ems.domain.common.error

class HandledException(val type: ErrorType) : Exception(type.toString())
