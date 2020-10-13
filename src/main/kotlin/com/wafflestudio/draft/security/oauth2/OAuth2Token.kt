package com.wafflestudio.draft.security.oauth2

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class OAuth2Token : AbstractAuthenticationToken {
    private val principal: Any?
    private var accessToken: Any?

    constructor(principal: Any?, accessToken: Any) : super(null) {
        this.principal = principal
        this.accessToken = accessToken
        super.setAuthenticated(false)
    }

    constructor(principal: Any?, accessToken: Any?, authorities: Collection<GrantedAuthority?>?) : super(authorities) {
        this.principal = principal
        this.accessToken = accessToken
        super.setAuthenticated(true)
    }

    override fun getCredentials(): Any? {
        return accessToken
    }

    override fun getPrincipal(): Any? {
        return principal
    }

    override fun eraseCredentials() {
        super.eraseCredentials()
        accessToken = null
    }
}