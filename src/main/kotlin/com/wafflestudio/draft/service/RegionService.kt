package com.wafflestudio.draft.service

import com.wafflestudio.draft.dto.RegionDTO
import com.wafflestudio.draft.model.Region
import com.wafflestudio.draft.repository.RegionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class RegionService(private val regionRepository: RegionRepository) {

    fun findRegionById(id: Long?): Optional<Region> {
        return regionRepository.findById(id!!)
    }

    fun findRegionsByName(name: String?): List<RegionDTO.Summary> {
        return regionRepository.findByNameContainingWithoutGeometryData(name)
    }

    fun findRegionsByDepth3(depth3: String?, pageable: Pageable): Page<RegionDTO.Summary> {
        return regionRepository.findByDepth3ContainingWithoutGeometryData(depth3, pageable)
    }

    fun getRegions(): List<RegionDTO.SummaryWithRooms> {
        return regionRepository.findAllRegionWithoutGeometryData()
    }
}
