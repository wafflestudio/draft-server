package com.wafflestudio.draft.dto.response

import org.locationtech.jts.geom.Point

data class Location (
    var lat: Double, // 위도
    var lng: Double // 경도
) {
    constructor(point: Point) : this(
            point.y, point.x
    )
}
