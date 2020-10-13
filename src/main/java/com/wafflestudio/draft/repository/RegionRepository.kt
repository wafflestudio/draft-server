package com.wafflestudio.draft.repository

import com.wafflestudio.draft.model.Region
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RegionRepository : JpaRepository<Region?, Long?> {
    override fun findById(id: Long): Optional<Region?>
    fun findByNameContaining(name: String?): List<Region?>?
}