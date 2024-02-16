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
	val count: Long = 0
)
