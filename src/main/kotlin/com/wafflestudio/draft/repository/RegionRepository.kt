package com.wafflestudio.draft.repository

import com.wafflestudio.draft.dto.RegionDTO
import com.wafflestudio.draft.model.Region
import org.locationtech.jts.geom.Point
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface RegionRepository : JpaRepository<Region, Long> {
    override fun findById(id: Long): Optional<Region>

//    @Query("SELECT new Region(r.id, r.emdCode, r.depth1, r.depth2, r.depth3, r.name) FROM Region r")
//    fun findAllRegionWithoutGeometryData(): List<Region>

    @EntityGraph(attributePaths = ["courts"])
    @Query("""SELECT DISTINCT r FROM Region r""")
    fun findAllRegionWithoutGeometryData(): List<RegionDTO.SummaryWithCourts>

    fun findByNameContaining(name: String?): List<Region>

    @Query("""SELECT r.id, r.emdCode, r.depth1, r.depth2, r.depth3, r.name
        FROM Region r WHERE r.name LIKE CONCAT('%',:name,'%')""")
    fun findByNameContainingWithoutGeometryData(name: String?): List<Region>

    fun findByDepth3Containing(depth3: String?): List<Region>

    @Query("SELECT r.id, r.emdCode, r.depth1, r.depth2, r.depth3, r.name " +
            "FROM Region r WHERE r.depth3 LIKE CONCAT('%',:depth3,'%')")
    fun findByDepth3ContainingWithoutGeometryData(depth3: String?): List<Region>

    @Query("SELECT r.id, r.emdCode, r.depth1, r.depth2, r.depth3, r.name FROM Region r " +
            "WHERE CONTAINS(r.polygon, ST_SetSRID(ST_MakePoint(:lon,:lat),4326))=true")
    fun findRegionByPolygonContainsCoordinate(@Param("lat") lat: Double, @Param("lon") lon: Double): List<Region>?

    @Query("SELECT r.id, r.emdCode, r.depth1, r.depth2, r.depth3, r.name FROM Region r " +
            "WHERE CONTAINS(r.polygon, :point)=true")
    fun findRegionByPolygonContainsPoint(@Param("point") point: Point): List<Region>
}
