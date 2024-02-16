package com.wafflestudio.snugo.record.repository

import com.wafflestudio.snugo.record.model.RouteRecord
import com.wafflestudio.snugo.record.model.RouteType
import org.springframework.data.mongodb.repository.MongoRepository

interface RouteRecordRepository : MongoRepository<RouteRecord, String> {
	fun findAllByNickname(nickname: String): List<RouteRecord>
	fun findByUid(uid: String): List<RouteRecord>
	fun findByRouteType(routeType: RouteType): RouteRecord
}
