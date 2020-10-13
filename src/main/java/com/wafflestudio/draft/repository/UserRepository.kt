package com.wafflestudio.draft.repository

import com.wafflestudio.draft.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User?, Long?> {
    fun findByEmail(email: String?): Optional<User?>
    fun findByUsername(username: String?): Optional<User?>?
}