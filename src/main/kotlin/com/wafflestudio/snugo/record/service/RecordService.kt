package com.wafflestudio.snugo.record.service

import com.wafflestudio.snugo.building.repository.BuildingRepository
import com.wafflestudio.snugo.common.auth.model.AuthUserInfo
import com.wafflestudio.snugo.common.error.BusinessException
import com.wafflestudio.snugo.common.error.ErrorType
import com.wafflestudio.snugo.common.util.GeoUtil
import com.wafflestudio.snugo.record.model.*
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
	private val buildingRepository: BuildingRepository,
	private val geoUtil: GeoUtil
) {
	fun getMyRecordList(uid: String, page: Int, size: Int): RecordPageResponse {
		val pageResult = routeRecordRepository.findByUserId(uid, PageRequest.of(page, size, Sort.Direction.DESC, "startTime"))
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
			routeType ?: throw BusinessException(ErrorType.ROUTE_TYPE_ID_NOT_FOUND),
			PageRequest.of(page, size, Sort.Direction.DESC, "startTime")
		)
		return RecordPageResponse(
			result = pageResult.content,
			hasNext = pageResult.hasNext(),
			total_count = pageResult.totalElements
		)
	}

	fun getTopWithRouteId(routeTypeId: String, page: Int, size: Int): RecordPageResponse {
		val routeType = routeTypeRepository.findById(routeTypeId).getOrNull()
		val pageResult = routeRecordRepository.findByRouteType(
			routeType ?: throw BusinessException(ErrorType.ROUTE_TYPE_ID_NOT_FOUND),
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

	fun getMyRank(id: String, userId: String): RankResponse {
		val routeType = routeTypeRepository.findById(id).getOrNull()
		val allRecords = routeRecordRepository.findByRouteTypeOrderByDurationAsc(routeType!!)
		for (i in 0L..allRecords.size - 1L) {
			if (allRecords.get(i.toInt()).userId == userId) return RankResponse(myRank = i + 1, total = allRecords.size.toLong())
		}
		return RankResponse(0, allRecords.size.toLong())
	}

	fun getMyRankForAll(userId: String): Double {
		val allRouteType = routeTypeRepository.findAll()
		var recordCount = 0
		var totalPercentage = 0.0
		allRouteType.forEach {
			val currentRankResponse = getMyRank(it.id!!, userId)
			if (currentRankResponse.myRank > 0) {
				recordCount += 1
				totalPercentage += (currentRankResponse.myRank - 1).toDouble() / currentRankResponse.total.toDouble()
			}
		}
		return totalPercentage / recordCount.toDouble()
	}

	fun uploadRecord(authUserInfo: AuthUserInfo, route: Route) {
		val buildingList = route.buildings.mapNotNull { buildingRepository.findById(it).getOrNull() }
		val routeType: RouteType =
			routeTypeRepository.findByBuildings(buildingList)
				?: routeTypeRepository.save(
					RouteType(buildings = buildingList, count = 0, avgPathLength = 0.0, avgTime = 0.0)
				)
		val totalTime: Double = route.duration + routeType.count * routeType.avgTime
		val totalPathLength = geoUtil.getPathLength(route.path) + routeType.avgPathLength * routeType.count
		routeType.count += 1
		routeType.avgTime = totalTime / routeType.count.toDouble()
		routeType.avgPathLength = totalPathLength / routeType.count.toDouble()
		routeTypeRepository.save(routeType)
		val topOfRouteType = routeRecordRepository.findFirstByRouteTypeOrderByDurationAsc(routeType)
		routeRecordRepository.save(
			RouteRecord(
				nickname = authUserInfo.nickname,
				userId = authUserInfo.uid,
				duration = route.duration,
				path = route.path.map { it.key.toLong() to it.value }.toMap(),
				routeType = routeType,
				startTime = route.startTime,
				high = if (topOfRouteType == null) {
					true
				} else if (topOfRouteType.duration > route.duration) {
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
