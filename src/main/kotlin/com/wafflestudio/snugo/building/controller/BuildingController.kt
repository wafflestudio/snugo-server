package com.wafflestudio.snugo.building.controller

import com.wafflestudio.snugo.building.model.Building
import com.wafflestudio.snugo.building.model.BuildingSection
import com.wafflestudio.snugo.building.repository.BuildingRepository
import com.wafflestudio.snugo.common.error.BusinessException
import com.wafflestudio.snugo.common.error.ErrorType
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class BuildingController(
	private val buildingRepository: BuildingRepository
) {

	@Operation(
		summary = "모든 건물 리스트 조회"
	)
	@GetMapping("/v1/buildings")
	fun getBuildingsList(): List<Building> {
		return buildingRepository.findAll()
	}

	@Operation(
		summary = "특정 섹션에 해당하는 모든 건물 리스트 조회"
	)
	@GetMapping("/v1/buildings/section/{section}")
	fun getBuildingsListBySection(@PathVariable section: BuildingSection): List<Building> {
		return buildingRepository.findAllBySection(section)
	}

	@Operation(
		summary = "건물 ID로 특정 건물 정보 조회"
	)
	@GetMapping("/v1/buildings/{buildingId}")
	fun getBuildingById(@PathVariable buildingId: String):Building {
		return buildingRepository.findById(buildingId).orElseThrow { BusinessException(ErrorType.BUILDING_ID_NOT_FOUND) }
	}

	@Operation(
		summary = "건물정보 추가. (Admin 용)"
	)
	@PostMapping("/v1/buildings/insert")
	fun insertBuildings(@RequestBody buildingList: List<Building>) {
		buildingRepository.saveAll(buildingList)
	}

	@Operation(
		summary = "모든 건물 정보 삭제. (Admin 용)"
	)

	@DeleteMapping("/v1/buildings")
	fun deleteBuildings() {
		buildingRepository.deleteAll()
	}
}
