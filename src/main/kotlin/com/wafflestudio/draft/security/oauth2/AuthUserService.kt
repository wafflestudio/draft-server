package com.wafflestudio.draft.security.oauth2

import com.wafflestudio.draft.model.User
import com.wafflestudio.draft.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthUserService(private val userRepository: UserRepository? = null) {
//    @Autowired
//    private val userRepository: UserRepository? = null

    @Transactional
    fun loadUserByEmail(email: String?): User? {
        return userRepository!!.findByEmail(email)
    }

    fun checkUserExists(username: String?): Boolean {
        return userRepository!!.findByEmail(username) != null
    }

    fun saveUser(user: User?): User {
        return userRepository!!.save(user!!)
    }
}
