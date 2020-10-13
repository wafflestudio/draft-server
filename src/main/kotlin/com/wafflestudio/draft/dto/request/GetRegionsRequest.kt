package com.wafflestudio.draft.dto.request

import kotlin.jvm.Throws
import com.wafflestudio.draft.service.FCMService
import com.wafflestudio.draft.service.FCMInitializer
import com.wafflestudio.draft.security.oauth2.client.KakaoOAuth2Client
import com.wafflestudio.draft.security.oauth2.client.FacebookOAuth2Client
import com.wafflestudio.draft.security.JwtTokenProvider

data class GetRegionsRequest (
    private val name: String? = null
)