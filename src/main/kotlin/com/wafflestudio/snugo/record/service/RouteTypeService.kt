package com.wafflestudio.snugo.record.service

import com.wafflestudio.snugo.record.model.RouteTypePageResponse
import com.wafflestudio.snugo.record.repository.RouteTypeRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class RouteTypeService(
	private val routeTypeRepository: RouteTypeRepository
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
}
