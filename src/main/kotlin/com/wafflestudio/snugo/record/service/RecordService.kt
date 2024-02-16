package com.wafflestudio.snugo.record.service

import com.wafflestudio.snugo.common.auth.model.AuthUserInfo
import com.wafflestudio.snugo.record.model.Route
import com.wafflestudio.snugo.record.model.RouteRecord
import com.wafflestudio.snugo.record.model.RouteType
import com.wafflestudio.snugo.record.repository.RouteRecordRepository
import com.wafflestudio.snugo.record.repository.RouteTypeRepository
import org.springframework.stereotype.Service

@Service
class RecordService(
	private val routeRecordRepository: RouteRecordRepository,
	private val routeTypeRepository: RouteTypeRepository
) {
	fun getMyRecordList(uid: String): List<RouteRecord> {
		return routeRecordRepository.findByUid(uid)
	}

	fun uploadRecord(authUserInfo: AuthUserInfo, route: Route) {
		val routeType: RouteType =
			routeTypeRepository.findByBuildings(route.buildings)
				?: routeTypeRepository.save(
					RouteType(buildings = route.buildings, count = 0)
				)
		routeRecordRepository.save(
			RouteRecord(
				nickname = authUserInfo.nickname,
				uid = authUserInfo.uid,
				duration = route.duration,
				path = route.path.map { it.key.toLong() to it.value }.toMap(),
				routeType = routeType,
				startTime = route.startTime
			)
		)
	}

	fun getRecommendedRouteList(authUserInfo: AuthUserInfo) {
		// TODO
		return
	}
}
