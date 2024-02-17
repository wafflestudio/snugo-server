package com.wafflestudio.snugo.record.model

data class RouteTypePageResponse(
	val result: List<RouteType>,
	val total_count: Long,
	val hasNext: Boolean
)
