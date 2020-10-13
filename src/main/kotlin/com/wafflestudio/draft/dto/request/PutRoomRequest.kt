package com.wafflestudio.draft.dto.request

import com.wafflestudio.draft.model.enums.RoomStatus
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class PutRoomRequest(
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        var startTime: LocalDateTime? = null,

        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        var endTime: LocalDateTime? = null,
        var name: String? = null,
        var courtId: Long? = null,
        var status: RoomStatus? = null
)
