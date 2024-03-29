package com.wafflestudio.snugo.record.model

import com.wafflestudio.snugo.building.model.Building
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "routetype")
data class RouteType(
	@Id
	val id: String? = null,
	@Indexed(unique = true)
	val buildings: List<Building>,
	var count: Long = 0,
	var avgPathLength: Double,
	var avgTime: Double,
)
