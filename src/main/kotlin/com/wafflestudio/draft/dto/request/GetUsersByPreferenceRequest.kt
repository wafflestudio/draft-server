package com.wafflestudio.draft.dto.request

import org.springframework.format.annotation.DateTimeFormat
import java.time.DayOfWeek
import java.time.LocalTime

class GetUsersByPreferenceRequest(
        var regionName: String? = null,
        var dayOfWeek: DayOfWeek? = null,

        @field:DateTimeFormat(pattern = "HHmmss")
        var startTime: LocalTime? = null,

        @field:DateTimeFormat(pattern = "HHmmss")
        var endTime: LocalTime? = null
)
