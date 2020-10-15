package com.wafflestudio.draft.dto.request

import java.time.LocalDateTime

data class CreateRoomRequest(
        var startTime: LocalDateTime,
        var endTime: LocalDateTime,
        var name: String,
        var courtId: Long
)
