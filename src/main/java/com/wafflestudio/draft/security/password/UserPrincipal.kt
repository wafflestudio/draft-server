package com.wafflestudio.draft.security.password

import com.wafflestudio.draft.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.stream.Collectors

class UserPrincipal(user: User?) : User(user!!.username, user.email), UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        if (super.getRoles() == null) return ArrayList()
        val roles = Arrays.asList(*super.getRoles()!!.split(",").toTypedArray())
        return roles.stream().map { role: String? -> SimpleGrantedAuthority(role) }.collect(Collectors.toList())
    }

    override fun getPassword(): String {
        return super.getPassword()
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

    init {
        super.setId(user!!.id)
        super.setPassword(user.password)
        super.setRoles(user.roles)
        super.setDevices(user.devices)
    }
}