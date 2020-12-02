package com.wafflestudio.draft.repository

import com.wafflestudio.draft.dto.response.RegionResponse
import com.wafflestudio.draft.model.Region
import org.locationtech.jts.geom.Point
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface RegionRepository : JpaRepository<Region?, Long?> {
    override fun findById(id: Long): Optional<Region?>
    fun findByNameContaining(name: String?): List<RegionResponse>?

    @Query("SELECT r.id,r.name,r.depth1,r.depth2,r.depth3 FROM Region r " +
            "WHERE CONTAINS(r.polygon, ST_SetSRID(ST_MakePoint(:lon,:lat),4326))=true")
    fun findRegionByPolygonContainsCoordinate(@Param("lat") lat: Double, @Param("lon") lon: Double): List<RegionResponse>?

    @Query("SELECT r.id,r.name,r.depth1,r.depth2,r.depth3 FROM Region r " +
            "WHERE CONTAINS(r.polygon, :point)=true")
    fun findRegionByPolygonContainsPoint(@Param("point") point: Point): List<RegionResponse>?
}
