package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.enums.RoomStatus
import java.time.LocalDateTime

data class RoomResponse(
        var id: Long? = null,
        var roomStatus: RoomStatus? = null,
        var startTime: LocalDateTime? = null,
        var endTime: LocalDateTime? = null,
        var name: String? = null,
        var createdAt: LocalDateTime? = null,
        var ownerId: Long? = null,
        var courtId: Long? = null,
        var participants: List<UserInformationResponse>? = null
) {
    constructor(room: Room): this() {
        this.id = room.id
        this.roomStatus = room.status
        this.startTime = room.startTime
        this.endTime = room.endTime
        this.name = room.name
        this.createdAt = room.createdAt
        this.ownerId = room.owner!!.id
        this.courtId = room.court!!.id
        val userResponses: MutableList<UserInformationResponse> = mutableListOf()
        room.participants.map { userResponses.add(UserInformationResponse(it.user)) }
        this.participants = userResponses
    }
}
