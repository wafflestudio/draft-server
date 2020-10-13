package com.wafflestudio.draft.dto.request

import org.springframework.format.annotation.DateTimeFormat
import java.time.DayOfWeek
import java.time.LocalTime

class GetUsersByPreferenceRequest {
    var regionName: String? = null
    var dayOfWeek: DayOfWeek? = null

    @DateTimeFormat(pattern = "HHmmss")
    var startTime: LocalTime? = null

    @DateTimeFormat(pattern = "HHmmss")
    var endTime: LocalTime? = null

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is GetUsersByPreferenceRequest) return false
        val other = o
        if (!other.canEqual(this as Any)) return false
        val `this$regionName`: Any? = regionName
        val `other$regionName`: Any? = other.regionName
        if (if (`this$regionName` == null) `other$regionName` != null else `this$regionName` != `other$regionName`) return false
        val `this$dayOfWeek`: Any? = dayOfWeek
        val `other$dayOfWeek`: Any? = other.dayOfWeek
        if (if (`this$dayOfWeek` == null) `other$dayOfWeek` != null else `this$dayOfWeek` != `other$dayOfWeek`) return false
        val `this$startTime`: Any? = startTime
        val `other$startTime`: Any? = other.startTime
        if (if (`this$startTime` == null) `other$startTime` != null else `this$startTime` != `other$startTime`) return false
        val `this$endTime`: Any? = endTime
        val `other$endTime`: Any? = other.endTime
        return if (if (`this$endTime` == null) `other$endTime` != null else `this$endTime` != `other$endTime`) false else true
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is GetUsersByPreferenceRequest
    }

    override fun hashCode(): Int {
        val PRIME = 59
        var result = 1
        val `$regionName`: Any? = regionName
        result = result * PRIME + (`$regionName`?.hashCode() ?: 43)
        val `$dayOfWeek`: Any? = dayOfWeek
        result = result * PRIME + (`$dayOfWeek`?.hashCode() ?: 43)
        val `$startTime`: Any? = startTime
        result = result * PRIME + (`$startTime`?.hashCode() ?: 43)
        val `$endTime`: Any? = endTime
        result = result * PRIME + (`$endTime`?.hashCode() ?: 43)
        return result
    }

    override fun toString(): String {
        return "GetUsersByPreferenceRequest(regionName=" + regionName + ", dayOfWeek=" + dayOfWeek + ", startTime=" + startTime + ", endTime=" + endTime + ")"
    }
}