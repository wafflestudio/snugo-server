package com.wafflestudio.snugo.building.controller

import com.wafflestudio.snugo.building.model.Building
import com.wafflestudio.snugo.building.model.BuildingSection
import com.wafflestudio.snugo.building.repository.BuildingRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class BuildingController(
	private val buildingRepository: BuildingRepository
) {
	@GetMapping("/v1/buildings")
	fun getBuildingsList(): List<Building> {
		return buildingRepository.findAll()
	}

	@GetMapping("/v1/buildings/{section}")
	fun getBuildingsListBySection(@PathVariable section: BuildingSection): List<Building> {
		return buildingRepository.findAllBySection(section)
	}

	@PostMapping("/v1/buildings/insert")
	fun insertBuildings(@RequestBody buildingList: List<Building>) {
		buildingRepository.saveAll(buildingList)
	}

	@DeleteMapping("/v1/buildings")
	fun deleteBuildings() {
		buildingRepository.deleteAll()
	}
}
