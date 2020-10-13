package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.Region
import kotlin.jvm.Throws
import com.wafflestudio.draft.service.FCMService
import com.wafflestudio.draft.service.FCMInitializer
import com.wafflestudio.draft.security.oauth2.client.KakaoOAuth2Client
import com.wafflestudio.draft.security.oauth2.client.FacebookOAuth2Client
import com.wafflestudio.draft.security.JwtTokenProvider

class RegionResponse(region: Region) {
    var id: Long? = null
    var name: String? =null
    var depth1: String? = null
    private var depth2: String? = null
    private var depth3: String? = null

    init {
        id = region.id
        name = region.name
        depth1 = region.depth1
        depth2 = region.depth2
        depth3 = region.depth3
    }
}