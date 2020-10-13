package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.enums.RoomStatus
import java.time.LocalDateTime

data class RoomResponse (
    var id: Long? = null,
    var roomStatus: RoomStatus? = null,
    var startTime: LocalDateTime? = null,
    var endTime: LocalDateTime? = null,
    var name: String? = null,
    var createdAt: LocalDateTime? = null,
    var ownerId: Long? = null,
    var courtId: Long? = null,
    var participants: ParticipantsResponse? = null,
){
    constructor(room: Room?) {
        id = room!!.id
        roomStatus = room.status
        startTime = room.startTime
        endTime = room.endTime
        name = room.name
        createdAt = room.createdAt
        ownerId = room.owner!!.id
        courtId = room.court!!.id
    }

}