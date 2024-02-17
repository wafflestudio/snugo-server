package com.wafflestudio.snugo.record.controller

import com.wafflestudio.snugo.common.auth.model.AuthUserInfo
import com.wafflestudio.snugo.record.model.RecordPageResponse
import com.wafflestudio.snugo.record.model.RouteUploadRequest
import com.wafflestudio.snugo.record.service.RecordService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/record")
class RecordController(
	private val recordService: RecordService
) {
	@PostMapping("/upload")
	fun uploadRecord(@AuthenticationPrincipal authUserInfo: AuthUserInfo, @RequestBody routeUploadRequest: RouteUploadRequest) {
		recordService.uploadRecord(authUserInfo, routeUploadRequest.route)
	}

	@GetMapping("/my")
	fun getMyRecordList(
		@AuthenticationPrincipal authUserInfo: AuthUserInfo,
		@RequestParam page: Int,
		@RequestParam size: Int,
	): RecordPageResponse {
		return recordService.getMyRecordList(authUserInfo.uid, page, size)
	}

	@GetMapping("/recent")
	fun getRecentRecordList(
		@AuthenticationPrincipal authUserInfo: AuthUserInfo,
		@RequestParam page: Int,
		@RequestParam size: Int,
	): RecordPageResponse {
		return recordService.getRecentRecordList(page, size)
	}

	@GetMapping("/newhigh")
	fun getNewRecordList(
		@AuthenticationPrincipal authUserInfo: AuthUserInfo,
		@RequestParam page: Int,
		@RequestParam size: Int,
	): RecordPageResponse {
		return recordService.getUpdatedHighScoreList(page, size)
	}

	@GetMapping("/top/{id}")
	fun getTopWithRouteId(
		@AuthenticationPrincipal authUserInfo: AuthUserInfo,
		@PathVariable id: String,
		@RequestParam page: Int,
		@RequestParam size: Int,
	): RecordPageResponse {
		return recordService.getTopWithRouteId(id, page, size)
	}

	@GetMapping("/recent/{id}")
	fun getRecentWithRouteId(
		@AuthenticationPrincipal authUserInfo: AuthUserInfo,
		@PathVariable id: String,
		@RequestParam page: Int,
		@RequestParam size: Int,
	): RecordPageResponse {
		return recordService.getRecentWithRouteId(id, page, size)
	}
}
