package com.wafflestudio.draft.dto.request

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class GetRoomsRequest(
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        var startTime: LocalDateTime? = null,

        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        var endTime: LocalDateTime? = null,
        var name: String? = null,
        var regionId: Long? = null
)
