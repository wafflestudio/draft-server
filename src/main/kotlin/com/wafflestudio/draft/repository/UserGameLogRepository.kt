package com.wafflestudio.draft.repository

import com.wafflestudio.draft.model.UserGameLog
import org.springframework.data.jpa.repository.JpaRepository

interface UserGameLogRepository : JpaRepository<UserGameLog?, Long?> 