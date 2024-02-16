package com.wafflestudio.snugo.record.controller

import com.wafflestudio.snugo.common.auth.model.AuthUserInfo
import com.wafflestudio.snugo.record.model.RouteRecordListResponse
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
	fun getMyRecordList(@AuthenticationPrincipal authUserInfo: AuthUserInfo): RouteRecordListResponse {
		val record = recordService.getMyRecordList(authUserInfo.uid)
		return RouteRecordListResponse(record)
	}

	@GetMapping("/top")
	fun getTopRecordList(@AuthenticationPrincipal authUserInfo: AuthUserInfo): RouteRecordListResponse {
		//TODO
		return RouteRecordListResponse(listOf())
	}
}
