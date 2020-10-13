package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.Preference
import java.time.DayOfWeek
import java.time.LocalTime

class PreferenceResponse {
    var id: Long? = null
    var startAt: LocalTime? = null
    var endAt: LocalTime? = null
    var regionId: Long? = null
    var dayOfWeek: DayOfWeek? = null

    constructor(preference: Preference?) {
        id = preference!!.id
        startAt = preference.startAt
        endAt = preference.endAt
        regionId = preference.region!!.id
        dayOfWeek = preference.dayOfWeek
    }

    constructor() {}

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is PreferenceResponse) return false
        val other = o
        if (!other.canEqual(this as Any)) return false
        val `this$id`: Any? = id
        val `other$id`: Any? = other.id
        if (if (`this$id` == null) `other$id` != null else `this$id` != `other$id`) return false
        val `this$startAt`: Any? = startAt
        val `other$startAt`: Any? = other.startAt
        if (if (`this$startAt` == null) `other$startAt` != null else `this$startAt` != `other$startAt`) return false
        val `this$endAt`: Any? = endAt
        val `other$endAt`: Any? = other.endAt
        if (if (`this$endAt` == null) `other$endAt` != null else `this$endAt` != `other$endAt`) return false
        val `this$regionId`: Any? = regionId
        val `other$regionId`: Any? = other.regionId
        if (if (`this$regionId` == null) `other$regionId` != null else `this$regionId` != `other$regionId`) return false
        val `this$dayOfWeek`: Any? = dayOfWeek
        val `other$dayOfWeek`: Any? = other.dayOfWeek
        return if (if (`this$dayOfWeek` == null) `other$dayOfWeek` != null else `this$dayOfWeek` != `other$dayOfWeek`) false else true
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is PreferenceResponse
    }

    override fun hashCode(): Int {
        val PRIME = 59
        var result = 1
        val `$id`: Any? = id
        result = result * PRIME + (`$id`?.hashCode() ?: 43)
        val `$startAt`: Any? = startAt
        result = result * PRIME + (`$startAt`?.hashCode() ?: 43)
        val `$endAt`: Any? = endAt
        result = result * PRIME + (`$endAt`?.hashCode() ?: 43)
        val `$regionId`: Any? = regionId
        result = result * PRIME + (`$regionId`?.hashCode() ?: 43)
        val `$dayOfWeek`: Any? = dayOfWeek
        result = result * PRIME + (`$dayOfWeek`?.hashCode() ?: 43)
        return result
    }

    override fun toString(): String {
        return "PreferenceResponse(id=" + id + ", startAt=" + startAt + ", endAt=" + endAt + ", regionId=" + regionId + ", dayOfWeek=" + dayOfWeek + ")"
    }
}