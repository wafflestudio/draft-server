package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.Preference
import java.time.DayOfWeek
import java.time.LocalTime

data class PreferenceResponse(
        var id: Long? = null,
        var startAt: LocalTime? = null,
        var endAt: LocalTime? = null,
        var regionId: Long? = null,
        var dayOfWeek: DayOfWeek? = null
) {
    constructor(preference: Preference?) {
        id = preference!!.id
        startAt = preference.startAt
        endAt = preference.endAt
        regionId = preference.region!!.id
        dayOfWeek = preference.dayOfWeek
    }
}
