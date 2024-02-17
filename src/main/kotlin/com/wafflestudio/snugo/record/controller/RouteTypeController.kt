package com.wafflestudio.snugo.record.controller

import com.wafflestudio.snugo.common.auth.model.AuthUserInfo
import com.wafflestudio.snugo.record.model.RouteTypePageResponse
import com.wafflestudio.snugo.record.service.RouteTypeService
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
	@GetMapping("/popular")
	fun getPopularList(
		@AuthenticationPrincipal authUserInfo: AuthUserInfo,
		@RequestParam page: Int,
		@RequestParam size: Int
	): RouteTypePageResponse {
		return routeTypeService.getPopularList(page, size)
	}

	@GetMapping("/recommend")
	fun getRecommendList(
		@AuthenticationPrincipal authUserInfo: AuthUserInfo,
		@RequestParam lat: Double,
		@RequestParam lng: Double
	): RouteTypePageResponse {
		return routeTypeService.getRecommendList(lat, lng)
	}
}
