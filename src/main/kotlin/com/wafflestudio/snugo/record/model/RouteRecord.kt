package com.wafflestudio.snugo.record.model

import com.wafflestudio.snugo.building.model.LatLng
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "routerecord")
data class RouteRecord(
	@Id
	val id: String? = null,
	val userId: String,
	val nickname: String,
	val routeType: RouteType,
	val path: Map<Long, LatLng>, // 타임스탬프 + 위경도
	val startTime: Long,
	val duration: Long,
	var high: Boolean
)
