package com.wafflestudio.draft.security.oauth2.client

import org.springframework.security.core.AuthenticationException

interface OAuth2Client {
    @Throws(AuthenticationException::class)
    fun userInfo(accessToken: String?): OAuth2Response
}