package com.wafflestudio.snugo.record.service

import com.wafflestudio.snugo.building.repository.BuildingRepository
import com.wafflestudio.snugo.common.auth.model.AuthUserInfo
import com.wafflestudio.snugo.record.model.Route
import com.wafflestudio.snugo.record.model.RouteRecord
import com.wafflestudio.snugo.record.model.RouteType
import com.wafflestudio.snugo.record.repository.RouteRecordRepository
import com.wafflestudio.snugo.record.repository.RouteTypeRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class RecordService(
	private val routeRecordRepository: RouteRecordRepository,
	private val routeTypeRepository: RouteTypeRepository,
	private val buildingRepository: BuildingRepository
) {
	fun getMyRecordList(uid: String): List<RouteRecord> {
		return routeRecordRepository.findByUid(uid)
	}

	fun uploadRecord(authUserInfo: AuthUserInfo, route: Route) {
		val buildingList = route.buildings.mapNotNull { buildingRepository.findById(it).getOrNull() }
		val routeType: RouteType =
			routeTypeRepository.findByBuildings(buildingList)
				?: routeTypeRepository.save(
					RouteType(buildings = buildingList, count = 0)
				)
		routeType.count += 1
		routeTypeRepository.save(routeType)
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
