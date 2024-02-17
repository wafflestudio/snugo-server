package com.wafflestudio.snugo.common.util

import com.wafflestudio.snugo.building.model.LatLng
import org.springframework.stereotype.Component
import kotlin.math.abs

const val EARTH_RADIUS = 6371

@Component
class GeoUtil {
	fun getDistance(a: LatLng, b: LatLng): Double {
		val lat1Rad = Math.toRadians(a.lat)
		val lng1Rad = Math.toRadians(a.lng)
		val lat2Rad = Math.toRadians(b.lat)
		val lng2Rad = Math.toRadians(b.lng)
		val dx = abs(lng1Rad - lng2Rad) * EARTH_RADIUS
		val dy = abs(lat1Rad - lat2Rad) * EARTH_RADIUS
		return Math.sqrt(dx * dx + dy * dy)
	}
}
