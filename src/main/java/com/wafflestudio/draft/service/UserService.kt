package com.wafflestudio.draft.service

import com.wafflestudio.draft.model.User
import com.wafflestudio.draft.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService {
    @Autowired
    private val userRepository: UserRepository? = null
    fun findUser(email: String?): Optional<User?>? {
        return userRepository!!.findByEmail(email)
    }
}