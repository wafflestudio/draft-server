package com.wafflestudio.draft.repository

import com.wafflestudio.draft.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User?, Long?> {
    fun findByEmail(email: String?): User?
    fun findByUsername(username: String?): User?
    fun existsUserByUsername(username: String?): Boolean
}