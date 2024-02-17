package com.wafflestudio.snugo.record.repository

import com.wafflestudio.snugo.building.model.Building
import com.wafflestudio.snugo.record.model.RouteType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface RouteTypeRepository : MongoRepository<RouteType, String> {
	fun findByBuildings(buildings: List<Building>): RouteType?
	override fun findAll(pageable: Pageable): Page<RouteType>
}
