package com.wafflestudio.snugo.record.model

import com.wafflestudio.snugo.building.model.LatLng

data class Route(
	val buildings: List<String>,
	val path: Map<String, LatLng>, // 타임스탬프 + 위경도
	val startTime: Long,
	val duration: Long,
)
