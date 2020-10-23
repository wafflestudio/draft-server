package com.wafflestudio.draft.service

import com.wafflestudio.draft.model.Court
import com.wafflestudio.draft.repository.CourtRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class CourtService {
    @Autowired
    private val courtRepository: CourtRepository? = null

    fun getCourtById(id: Long?): Optional<Court?> {
        return courtRepository!!.findById(id!!)
    }
}
