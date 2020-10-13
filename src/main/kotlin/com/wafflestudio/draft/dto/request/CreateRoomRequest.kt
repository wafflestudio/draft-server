package com.wafflestudio.draft.dto.request

import java.time.LocalDateTime
import javax.validation.constraints.NotNull

class CreateRoomRequest {
    @NotNull
    var startTime: LocalDateTime? = null

    @NotNull
    var endTime: LocalDateTime? = null

    @NotNull
    var name: String? = null

    @NotNull
    var courtId: Long? = null

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is CreateRoomRequest) return false
        val other = o
        if (!other.canEqual(this as Any)) return false
        val `this$startTime`: Any? = startTime
        val `other$startTime`: Any? = other.startTime
        if (if (`this$startTime` == null) `other$startTime` != null else `this$startTime` != `other$startTime`) return false
        val `this$endTime`: Any? = endTime
        val `other$endTime`: Any? = other.endTime
        if (if (`this$endTime` == null) `other$endTime` != null else `this$endTime` != `other$endTime`) return false
        val `this$name`: Any? = name
        val `other$name`: Any? = other.name
        if (if (`this$name` == null) `other$name` != null else `this$name` != `other$name`) return false
        val `this$courtId`: Any? = courtId
        val `other$courtId`: Any? = other.courtId
        return if (if (`this$courtId` == null) `other$courtId` != null else `this$courtId` != `other$courtId`) false else true
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is CreateRoomRequest
    }

    override fun hashCode(): Int {
        val PRIME = 59
        var result = 1
        val `$startTime`: Any? = startTime
        result = result * PRIME + (`$startTime`?.hashCode() ?: 43)
        val `$endTime`: Any? = endTime
        result = result * PRIME + (`$endTime`?.hashCode() ?: 43)
        val `$name`: Any? = name
        result = result * PRIME + (`$name`?.hashCode() ?: 43)
        val `$courtId`: Any? = courtId
        result = result * PRIME + (`$courtId`?.hashCode() ?: 43)
        return result
    }

    override fun toString(): String {
        return "CreateRoomRequest(startTime=" + startTime + ", endTime=" + endTime + ", name=" + name + ", courtId=" + courtId + ")"
    }
}