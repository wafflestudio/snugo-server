package com.wafflestudio.snugo.record.service

import com.wafflestudio.snugo.building.repository.BuildingRepository
import com.wafflestudio.snugo.common.auth.model.AuthUserInfo
import com.wafflestudio.snugo.record.model.RecordPageResponse
import com.wafflestudio.snugo.record.model.Route
import com.wafflestudio.snugo.record.model.RouteRecord
import com.wafflestudio.snugo.record.model.RouteType
import com.wafflestudio.snugo.record.repository.RouteRecordRepository
import com.wafflestudio.snugo.record.repository.RouteTypeRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class RecordService(
	private val routeRecordRepository: RouteRecordRepository,
	private val routeTypeRepository: RouteTypeRepository,
	private val buildingRepository: BuildingRepository
) {
	fun getMyRecordList(uid: String, page: Int, size: Int): RecordPageResponse {
		val pageResult = routeRecordRepository.findByUid(uid, PageRequest.of(page, size, Sort.Direction.DESC, "startTime"))
		return RecordPageResponse(
			result = pageResult.content,
			hasNext = pageResult.hasNext(),
			total_count = pageResult.totalElements
		)
	}

	fun getRecentRecordList(page: Int, size: Int): RecordPageResponse {
		val pageResult = routeRecordRepository.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "startTime"))
		return RecordPageResponse(
			result = pageResult.content,
			hasNext = pageResult.hasNext(),
			total_count = pageResult.totalElements
		)
	}

	fun getRecentWithRouteId(routeTypeId: String, page: Int, size: Int): RecordPageResponse {
		val routeType = routeTypeRepository.findById(routeTypeId).getOrNull()
		val pageResult = routeRecordRepository.findByRouteType(
			routeType ?: RouteTypeNotFoundException,
			PageRequest.of(page, size, Sort.Direction.DESC, "startTime")
		)
		return RecordPageResponse(
			result = pageResult.content,
			hasNext = pageResult.hasNext(),
			total_count = pageResult.totalElements
		)
	}

	fun getFastestWithRouteId(routeTypeId: String, page: Int, size: Int): RecordPageResponse {
		val routeType = routeTypeRepository.findById(routeTypeId).getOrNull()
		val pageResult = routeRecordRepository.findByRouteType(
			routeType ?: RouteTypeNotFoundException,
			PageRequest.of(page, size, Sort.Direction.ASC, "duration")
		)
		return RecordPageResponse(
			result = pageResult.content,
			hasNext = pageResult.hasNext(),
			total_count = pageResult.totalElements
		)
	}

	fun getUpdatedHighScoreList(page: Int, size: Int): RecordPageResponse {
		val pageResult = routeRecordRepository.findByHigh(true, PageRequest.of(page, size, Sort.Direction.DESC, "startTime"))
		return RecordPageResponse(
			result = pageResult.content,
			hasNext = pageResult.hasNext(),
			total_count = pageResult.totalElements
		)
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
		val topOfRouteType = routeRecordRepository.findFirstByRouteTypeOrderByDurationAsc(routeType)
		routeRecordRepository.save(
			RouteRecord(
				nickname = authUserInfo.nickname,
				uid = authUserInfo.uid,
				duration = route.duration,
				path = route.path.map { it.key.toLong() to it.value }.toMap(),
				routeType = routeType,
				startTime = route.startTime,
				high = if (topOfRouteType.duration > route.duration) {
					topOfRouteType.high = false
					routeRecordRepository.save(topOfRouteType)
					true
				} else {
					false
				}
			)
		)
	}
}
