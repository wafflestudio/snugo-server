package com.wafflestudio.snugo.record.repository

import com.wafflestudio.snugo.record.model.RouteRecord
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface RouteRecordRepository : MongoRepository<RouteRecord, String> {
	fun findAllByUserId(uid: String, pageable: Pageable): Page<RouteRecord>
	fun findAllByRouteTypeId(routeTypeId: String, pageable: Pageable): Page<RouteRecord>
	fun findAllByRouteTypeIdOrderByDurationAsc(routeTypeId: String): List<RouteRecord>
	override fun findAll(pageable: Pageable): Page<RouteRecord>
	fun findByRouteTypeIdAndHighscoreyn(routeTypeId: String, highscoreyn: Boolean): RouteRecord?
	fun findAllByRouteTypeId(routeTypeId: String): List<RouteRecord>

	fun findAllByHighscoreyn(high: Boolean, pageable: Pageable): Page<RouteRecord>
}
