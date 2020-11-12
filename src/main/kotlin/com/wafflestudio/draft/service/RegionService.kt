package com.wafflestudio.draft.service

import com.wafflestudio.draft.model.Region
import com.wafflestudio.draft.repository.RegionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class RegionService {
    @Autowired
    private val regionRepository: RegionRepository? = null

    fun findRegionById(id: Long?): Optional<Region?>? {
        return regionRepository!!.findById(id!!)
    }

    fun findRegionsByName(name: String?): List<Region>? {
        return regionRepository!!.findByNameContaining(name)
    }

    fun getRegions(): MutableList<Region?> {
        return regionRepository!!.findAll()
    }
}
