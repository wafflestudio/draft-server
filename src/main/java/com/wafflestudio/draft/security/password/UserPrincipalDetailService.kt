package com.wafflestudio.draft.security.password

import com.wafflestudio.draft.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserPrincipalDetailService(private val userRepository: UserRepository) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(s: String): UserDetails {
        val user = userRepository.findByEmail(s)
                .orElseThrow { UsernameNotFoundException("User with email '%s' not found") }
        return UserPrincipal(user)
    }

}