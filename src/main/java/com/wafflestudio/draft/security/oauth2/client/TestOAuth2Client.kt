package com.wafflestudio.draft.security.oauth2.client

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class TestOAuth2Client : OAuth2Client {
    override fun userInfo(accessToken: String?): OAuth2Response {
        return OAuth2Response("TEST", "$accessToken@test.com", HttpStatus.OK)
    }

    companion object {
        const val OAUTH_TOKEN_PREFIX = "TEST"
    }
}