package com.wafflestudio.snugo.building.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "building")
data class Building(
	@Id
	val id: String? = null,
	val name: String?,
	val location: LatLng?,
	val section: BuildingSection?
)
