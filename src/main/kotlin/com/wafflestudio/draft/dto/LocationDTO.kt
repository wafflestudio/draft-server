package com.wafflestudio.draft.dto

import org.locationtech.jts.geom.Point

class LocationDTO {
    data class Reponse(
        var lat: Double, // 위도
        var lng: Double // 경도
    ) {
        constructor(point: Point) : this(
            point.y, point.x
        )
    }
}