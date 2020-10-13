package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.User

data class RoomsOfUserResponse(
        var id: Long? = null,
        var username: String? = null,
        var email: String? = null,
        var rooms: List<RoomResponse>? = null
) {
    constructor(user: User) {
        id = user.id
        username = user.username
        email = user.email
    }
}
