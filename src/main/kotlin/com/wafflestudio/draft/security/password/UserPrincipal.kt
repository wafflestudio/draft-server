package com.wafflestudio.draft.security.password

import com.wafflestudio.draft.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.stream.Collectors

class UserPrincipal(val user: User) : UserDetails {
    override fun getUsername(): String {
        return user.username
    }

    override fun getPassword(): String? {
        return user.password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        if (user.roles == null) return ArrayList()
        val roles = Arrays.asList(*user.roles!!.split(",").toTypedArray())
        return roles.stream().map { role: String? -> SimpleGrantedAuthority(role) }.collect(Collectors.toList())
    }


    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }


}
