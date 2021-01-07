package com.wafflestudio.draft.service

import com.wafflestudio.draft.error.CourtNotFoundException
import com.wafflestudio.draft.model.Court
import com.wafflestudio.draft.repository.CourtRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CourtService(private val courtRepository: CourtRepository) {
    fun getCourtById(id: Long): Court {
        return courtRepository.findById(id).orElseThrow(::CourtNotFoundException)!!
    }

    fun findCourtsByName(name: String?): List<Court> {
        return courtRepository.findByNameContaining(name)
    }

    fun findCourtsByRegionId(id: Long): List<Court>? {
        return courtRepository.findAllByRegion_Id(id)
    }
}
