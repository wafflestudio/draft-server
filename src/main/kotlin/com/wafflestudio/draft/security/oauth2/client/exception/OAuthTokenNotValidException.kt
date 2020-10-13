package com.wafflestudio.draft.security.oauth2.client.exception

import org.springframework.security.core.AuthenticationException

class OAuthTokenNotValidException : AuthenticationException {
    constructor(msg: String?, t: Throwable?) : super(msg, t) {}
    constructor(msg: String?) : super(msg) {}
}