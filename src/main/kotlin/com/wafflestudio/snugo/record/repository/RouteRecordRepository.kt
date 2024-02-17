package com.wafflestudio.snugo.record.repository

import com.wafflestudio.snugo.record.model.RouteRecord
import com.wafflestudio.snugo.record.model.RouteType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface RouteRecordRepository : MongoRepository<RouteRecord, String> {
	fun findAllByUserId(uid: String, pageable: Pageable): Page<RouteRecord>
	fun findAllByRouteType(routeType: RouteType, pageable: Pageable): Page<RouteRecord>
	fun findAllByRouteTypeOrderByDurationAsc(routeType: RouteType): List<RouteRecord>
	override fun findAll(pageable: Pageable): Page<RouteRecord>
	fun findFirstByRouteTypeOrderByDurationAsc(routeType: RouteType): RouteRecord?
	fun findAllByHighAndRouteType(high: Boolean, routeType: RouteType): List<RouteRecord>
	fun findAllByHigh(high: Boolean, pageable: Pageable): Page<RouteRecord>
}
