package com.wafflestudio.draft.security.oauth2.client.exception

import org.springframework.security.core.AuthenticationException
import kotlin.jvm.Throws
import com.wafflestudio.draft.service.FCMService
import com.wafflestudio.draft.service.FCMInitializer
import com.wafflestudio.draft.security.oauth2.client.KakaoOAuth2Client
import com.wafflestudio.draft.security.oauth2.client.FacebookOAuth2Client
import com.wafflestudio.draft.security.JwtTokenProvider

class SucceedOAuthUserNotFoundException : AuthenticationException {
    constructor(msg: String?, t: Throwable?) : super(msg, t) {}
    constructor(msg: String?) : super(msg) {}
}