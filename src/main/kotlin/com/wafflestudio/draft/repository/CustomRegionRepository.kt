package com.wafflestudio.draft.repository

import com.wafflestudio.draft.dto.RegionDTO

interface CustomRegionRepository {
    fun findAllRegionWithoutGeometryData(): List<RegionDTO.SummaryWithRooms>
}