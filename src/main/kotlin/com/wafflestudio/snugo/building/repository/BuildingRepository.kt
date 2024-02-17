package com.wafflestudio.snugo.building.repository

import com.wafflestudio.snugo.building.model.Building
import com.wafflestudio.snugo.building.model.BuildingSection
import org.springframework.data.mongodb.repository.MongoRepository

interface BuildingRepository : MongoRepository<Building, String> {
	fun findAllBySection(section: BuildingSection): List<Building>
}
