package com.wafflestudio.snugo.record.controller

import com.wafflestudio.snugo.common.auth.model.AuthUserInfo
import com.wafflestudio.snugo.record.model.RouteTypePageResponse
import com.wafflestudio.snugo.record.service.RouteTypeService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/routetype")
class RouteTypeController(
	private val routeTypeService: RouteTypeService
) {
	@Operation(summary = "많은 사람들이 기록을 제출한 코스")
	@GetMapping("/popular")
	fun getPopularList(
		@AuthenticationPrincipal authUserInfo: AuthUserInfo,
		@RequestParam page: Int,
		@RequestParam size: Int
	): RouteTypePageResponse {
		return routeTypeService.getPopularList(page, size)
	}

	@Operation(summary = "추천 코스, 200m 근방에서 출발하는 코스 인기 상위 10개")
	@GetMapping("/recommend")
	fun getRecommendList(
		@AuthenticationPrincipal authUserInfo: AuthUserInfo,
		@RequestParam lat: Double,
		@RequestParam lng: Double
	): RouteTypePageResponse {
		return routeTypeService.getRecommendList(lat, lng)
	}
}
