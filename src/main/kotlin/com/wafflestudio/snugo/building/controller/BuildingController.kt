package com.wafflestudio.snugo.building.controller

import com.wafflestudio.snugo.building.model.Building
import com.wafflestudio.snugo.building.repository.BuildingRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class BuildingController(
	private val buildingRepository: BuildingRepository
) {
	@GetMapping("/v1/buildings")
	fun getBuildingsList(): List<Building> {
		return buildingRepository.findAll()
	}
}
