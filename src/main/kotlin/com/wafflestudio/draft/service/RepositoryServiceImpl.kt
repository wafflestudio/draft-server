package com.wafflestudio.draft.service

import com.wafflestudio.draft.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RepositoryServiceImpl : RepositoryService {
    @Autowired
    private val userRepository: UserRepository? = null
}
