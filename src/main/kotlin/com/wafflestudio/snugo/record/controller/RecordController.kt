package com.wafflestudio.snugo.record.controller

import com.wafflestudio.snugo.common.auth.model.AuthUserInfo
import com.wafflestudio.snugo.record.model.RankResponse
import com.wafflestudio.snugo.record.model.RecordPageResponse
import com.wafflestudio.snugo.record.model.RouteUploadRequest
import com.wafflestudio.snugo.record.service.RecordService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/record")
class RecordController(
	private val recordService: RecordService
) {
	@Operation(summary = "기록 제출 api")
	@PostMapping("/upload")
	fun uploadRecord(@AuthenticationPrincipal authUserInfo: AuthUserInfo, @RequestBody routeUploadRequest: RouteUploadRequest) {
		recordService.uploadRecord(authUserInfo, routeUploadRequest.route)
	}

	@Operation(summary = "나의 기록 보기, 최근순")
	@GetMapping("/my")
	fun getMyRecordList(
		@AuthenticationPrincipal authUserInfo: AuthUserInfo,
		@RequestParam page: Int,
		@RequestParam size: Int,
	): RecordPageResponse {
		return recordService.getMyRecordList(authUserInfo.uid, page, size)
	}

	@Operation(summary = "전체 이용자의 최근 기록")
	@GetMapping("/recent")
	fun getRecentRecordList(
		@AuthenticationPrincipal authUserInfo: AuthUserInfo,
		@RequestParam page: Int,
		@RequestParam size: Int,
	): RecordPageResponse {
		return recordService.getRecentRecordList(page, size)
	}

	@Operation(summary = "새로 세워진 신기록, 최근순")
	@GetMapping("/newhigh")
	fun getNewRecordList(
		@AuthenticationPrincipal authUserInfo: AuthUserInfo,
		@RequestParam page: Int,
		@RequestParam size: Int,
	): RecordPageResponse {
		return recordService.getUpdatedHighScoreList(page, size)
	}

	@Operation(summary = "이 id를 갖는 경로(routeType)에 대해 시간 짧은 순")
	@GetMapping("/top/{id}")
	fun getTopWithRouteId(
		@AuthenticationPrincipal authUserInfo: AuthUserInfo,
		@PathVariable id: String,
		@RequestParam page: Int,
		@RequestParam size: Int,
	): RecordPageResponse {
		return recordService.getTopWithRouteId(id, page, size)
	}

	@Operation(summary = "이 id를 갖는 경로(routeType)에 대해 최근 순")
	@GetMapping("/recent/{id}")
	fun getRecentWithRouteId(
		@AuthenticationPrincipal authUserInfo: AuthUserInfo,
		@PathVariable id: String,
		@RequestParam page: Int,
		@RequestParam size: Int,
	): RecordPageResponse {
		return recordService.getRecentWithRouteId(id, page, size)
	}

	@Operation(summary = "이 id를 갖는 경로에 대해 나의 등수와 전체 기록 수")
	@GetMapping("/myrank/{id}")
	fun getMyRankForRouteType(
		@AuthenticationPrincipal authUserInfo: AuthUserInfo,
		@PathVariable id: String,
	): RankResponse {
		return recordService.getMyRank(id, authUserInfo.uid)
	}

	@Operation(summary = "전체 사용자 중 나의 위치(%), 나의 평균 등수 퍼센트")
	@GetMapping("/myrank")
	fun getMyRankForAll(
		@AuthenticationPrincipal authUserInfo: AuthUserInfo,
	): Double {
		return recordService.getMyRankForAll(authUserInfo.uid)
	}
}
