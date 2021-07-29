package com.wafflestudio.draft.dto

import com.wafflestudio.draft.model.Preference
import com.wafflestudio.draft.model.Region
import org.springframework.format.annotation.DateTimeFormat
import java.time.DayOfWeek
import java.time.LocalTime

class PreferenceDTO {
    class GetUsersByPreferenceRequest(
        var regionName: String? = null,
        var dayOfWeek: DayOfWeek? = null,

        @field:DateTimeFormat(pattern = "HHmmss")
        var startTime: LocalTime? = null,

        @field:DateTimeFormat(pattern = "HHmmss")
        var endTime: LocalTime? = null
    )

    data class SetPreferenceRequest(
        var regionId: Long? = null,
        var preferences: List<Preference>? = null
    )

    data class Response(
        var id: Long? = null,
        var startAt: LocalTime? = null,
        var endAt: LocalTime? = null,
        var regionId: Long? = null,
        var dayOfWeek: DayOfWeek? = null
    ) {
        constructor(preference: Preference?): this(
            preference!!.id,
            preference.startAt,
            preference.endAt,
            preference.region!!.id,
            preference.dayOfWeek
        )
    }

    class PreferenceInRegionResponse(
        var id: Long? = null,
        var name: String? = null,
        var depth1: String? = null,
        var depth2: String? = null,
        var depth3: String? = null,
        var preference: List<Response>? = null
    ) {
        constructor(region: Region, preference: List<Preference?>?): this(
            region.id,
            region.name,
            region.depth1,
            region.depth2,
            region.depth3,
            preference?.map {
                Response(it)
            }
        )
    }


}