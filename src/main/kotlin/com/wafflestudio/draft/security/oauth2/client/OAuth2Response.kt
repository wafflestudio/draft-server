package com.wafflestudio.draft.security.oauth2.client

import org.springframework.http.HttpStatus

data class OAuth2Response(var authServer: String, var email: String, var status: HttpStatus) {
}