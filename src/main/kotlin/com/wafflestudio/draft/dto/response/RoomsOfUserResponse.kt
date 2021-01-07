package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.dto.RoomDTO
import com.wafflestudio.draft.model.User

data class RoomsOfUserResponse(
        var id: Long? = null,
        var username: String? = null,
        var email: String? = null,
        var rooms: List<RoomDTO.Response>? = null
) {
    constructor(user: User) : this(
            user.id,
            user.username,
            user.email
    )
}
