package com.wafflestudio.snugo.record.service

import com.wafflestudio.snugo.building.model.LatLng
import com.wafflestudio.snugo.common.util.GeoUtil
import com.wafflestudio.snugo.record.model.RouteTypePageResponse
import com.wafflestudio.snugo.record.repository.RouteTypeRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class RouteTypeService(
	private val routeTypeRepository: RouteTypeRepository,
	private val geoUtil: GeoUtil
) {
	fun getPopularList(page: Int, size: Int): RouteTypePageResponse {
		val pageResult = routeTypeRepository.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "count"))
		return RouteTypePageResponse(
			result = pageResult.content,
			hasNext = pageResult.hasNext(),
			total_count = pageResult.totalElements
		)
	}

	// fun getByOrderByAvgPathLength

	fun getRecommendList(lat: Double, lng: Double): RouteTypePageResponse {
		val allRouteType = routeTypeRepository.findAll()
		return RouteTypePageResponse(
			result = allRouteType.mapNotNull {
				if (geoUtil.getDistance(it.buildings.get(0).location!!, LatLng(lat, lng)) < 0.2) {
					it
				} else {
					null
				}
			}.sortedByDescending { it.count }.subList(0, Math.min(10, allRouteType.size)),
			hasNext = false,
			total_count = allRouteType.size.toLong()
		)
	}
}
