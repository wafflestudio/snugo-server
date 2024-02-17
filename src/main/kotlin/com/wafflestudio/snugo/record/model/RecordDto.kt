package com.wafflestudio.snugo.record.model

import com.wafflestudio.snugo.building.model.LatLng

data class RecordDto(
	val id: String? = null,
	val userId: String,
	val nickname: String,
	val routeType: RouteType,
	val path: Map<Long, LatLng>, // 타임스탬프 + 위경도
	val startTime: Long,
	val duration: Long,
	var highscoreyn: Boolean
)
