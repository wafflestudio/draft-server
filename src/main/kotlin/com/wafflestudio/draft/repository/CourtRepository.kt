package com.wafflestudio.draft.repository

import com.wafflestudio.draft.model.Court
import org.springframework.data.jpa.repository.JpaRepository

interface CourtRepository : JpaRepository<Court?, Long?>
