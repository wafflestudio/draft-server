package com.wafflestudio.draft.security.password

import com.wafflestudio.draft.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class UserPrincipal(val user: User) : UserDetails {
    override fun getUsername(): String {
        return user.username
    }

    override fun getPassword(): String? {
        return user.password
    }

    override fun getAuthorities(): List<GrantedAuthority> {
        if (user.roles == null) return ArrayList()
        val roles = Arrays.asList(*user.roles!!.split(",").toTypedArray())
        return roles.map { role: String? -> SimpleGrantedAuthority(role) }
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
