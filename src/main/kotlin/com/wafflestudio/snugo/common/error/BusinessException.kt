package com.wafflestudio.snugo.common.error

open class BusinessException(val errorType: ErrorType) : RuntimeException(errorType.name)
