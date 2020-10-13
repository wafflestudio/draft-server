package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.User
import java.util.ArrayList
import kotlin.jvm.Throws
import com.wafflestudio.draft.service.FCMService
import com.wafflestudio.draft.service.FCMInitializer
import com.wafflestudio.draft.security.oauth2.client.KakaoOAuth2Client
import com.wafflestudio.draft.security.oauth2.client.FacebookOAuth2Client
import com.wafflestudio.draft.security.JwtTokenProvider

class RoomsOfUserResponse(user: User,roomData: List<Room>?) {
    var id: Long? = null
    var username: String? =null
    var email: String? = null
    var rooms: List<RoomResponse?>? = null

    init {
        id = user.id
        username = user.username
        email = user.email
        rooms = roomData?.let {  it.map { room -> RoomResponse(room) }}
    }
}