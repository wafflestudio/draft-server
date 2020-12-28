package com.wafflestudio.draft.service

import com.wafflestudio.draft.dto.RegionDTO
import com.wafflestudio.draft.model.Region
import com.wafflestudio.draft.repository.RegionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class RegionService(private val regionRepository: RegionRepository) {

    fun findRegionById(id: Long?): Optional<Region> {
        return regionRepository.findById(id!!)
    }

    fun findRegionsByName(name: String?): List<Region>? {
        return regionRepository.findByNameContainingWithoutGeometryData(name)
    }

    fun findRegionsByDepth3(depth3: String?): List<Region>? {
        return regionRepository.findByDepth3ContainingWithoutGeometryData(depth3)
    }

    fun getRegions(): List<RegionDTO.SummaryWithRooms> {
        return regionRepository.findAllRegionWithoutGeometryData()
    }
}
