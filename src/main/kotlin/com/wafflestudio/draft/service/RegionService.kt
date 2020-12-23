package com.wafflestudio.draft.service

import com.wafflestudio.draft.dto.RegionDTO
import com.wafflestudio.draft.dto.response.RegionResponse
import com.wafflestudio.draft.model.Region
import com.wafflestudio.draft.repository.RegionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class RegionService(private val regionRepository: RegionRepository) {

    fun findRegionById(id: Long?): Optional<Region?>? {
        return regionRepository.findById(id!!)
    }

    fun findRegionsByName(name: String?): List<RegionDTO.Info>? {
        return regionRepository.findByNameContaining(name)
    }

    fun findRegionsByDepth3(depth3: String?): List<RegionDTO.Info>? {
        return regionRepository.findByDepth3Containing(depth3)
    }

    fun getRegions(): List<Region?> {
        return regionRepository.findAll()
    }
}
