package com.wafflestudio.snugo.building.repository

import com.wafflestudio.snugo.building.model.Building
import org.springframework.data.mongodb.repository.MongoRepository

interface BuildingRepository : MongoRepository<Building, String>
