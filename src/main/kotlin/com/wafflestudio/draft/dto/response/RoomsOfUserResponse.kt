package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.User

class RoomsOfUserResponse(user: User) {
    var id: Long? = null
    var username: String? =null
    var email: String? = null
    var rooms: List<RoomResponse>? = null

    init {
        id = user.id
        username = user.username
        email = user.email
    }
}