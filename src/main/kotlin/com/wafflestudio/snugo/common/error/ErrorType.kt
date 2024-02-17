package com.wafflestudio.snugo.common.error

import org.springframework.http.HttpStatus

enum class ErrorType(
	val code: Int,
	val httpStatus: HttpStatus
) {
	// 400


	// 401


	// 403


	// 404
	BUILDING_ID_NOT_FOUND(14001, HttpStatus.NOT_FOUND),
	ROUTE_TYPE_ID_NOT_FOUND(14002, HttpStatus.NOT_FOUND),


	// 409
}
