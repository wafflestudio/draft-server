package com.wafflestudio.draft.service

import com.wafflestudio.draft.model.User
import com.wafflestudio.draft.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    private val userRepository: UserRepository? = null
    fun findUserByEmail(email: String?): User? {
        return userRepository!!.findByEmail(email)
    }

    fun existsUserByUsername(username: String?): Boolean {
        return userRepository!!.existsUserByUsername(username)
    }
}
