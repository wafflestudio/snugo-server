package com.wafflestudio.snugo.record.repository

import com.wafflestudio.snugo.record.model.RouteRecord
import com.wafflestudio.snugo.record.model.RouteType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface RouteRecordRepository : MongoRepository<RouteRecord, String> {
	fun findByUid(uid: String, pageable: Pageable): Page<RouteRecord>
	fun findByRouteType(routeType: RouteType, pageable: Pageable): Page<RouteRecord>
	override fun findAll(pageable: Pageable): Page<RouteRecord>
	fun findFirstByRouteTypeOrderByDurationAsc(routeType: RouteType): RouteRecord
	fun findByHigh(high: Boolean, pageable: Pageable): Page<RouteRecord>
}
