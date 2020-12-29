package com.wafflestudio.draft.repository

import com.wafflestudio.draft.dto.RegionDTO
import com.wafflestudio.draft.model.Region
import org.locationtech.jts.geom.Point
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface RegionRepository : JpaRepository<Region, Long>, CustomRegionRepository {
    override fun findById(id: Long): Optional<Region>

    fun findByNameContaining(name: String?): List<Region>

    @Query("""SELECT new com.wafflestudio.draft.dto.RegionDTO${'$'}Summary(r.id, r.depth1, r.depth2, r.depth3, r.name)
        FROM Region r WHERE r.name LIKE CONCAT('%',:name,'%')""")
    fun findByNameContainingWithoutGeometryData(name: String?): List<RegionDTO.Summary>

    fun findByDepth3Containing(depth3: String?): List<Region>

    @Query("""SELECT new com.wafflestudio.draft.dto.RegionDTO${'$'}Summary(r.id, r.depth1, r.depth2, r.depth3, r.name)
        FROM Region r WHERE r.depth3 LIKE CONCAT('%',:depth3,'%')""")
    fun findByDepth3ContainingWithoutGeometryData(depth3: String?): List<RegionDTO.Summary>

    @Query("""SELECT new com.wafflestudio.draft.dto.RegionDTO${'$'}Summary(r.id, r.depth1, r.depth2, r.depth3, r.name) 
        FROM Region r WHERE CONTAINS(r.polygon, ST_SetSRID(ST_MakePoint(:lon,:lat),4326))=true""")
    fun findRegionByPolygonContainsCoordinate(@Param("lat") lat: Double, @Param("lon") lon: Double): List<Region>?

    @Query("""SELECT new com.wafflestudio.draft.dto.RegionDTO${'$'}Summary(r.id, r.depth1, r.depth2, r.depth3, r.name)
        FROM Region r WHERE CONTAINS(r.polygon, :point)=true""")
    fun findRegionByPolygonContainsPoint(@Param("point") point: Point): List<Region>
}
