package com.wafflestudio.draft.dto.request

import com.wafflestudio.draft.model.enums.RoomStatus
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class PutRoomRequest {
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var startTime: LocalDateTime? = null

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var endTime: LocalDateTime? = null
    var name: String? = null
    var courtId: Long? = null
    var status: RoomStatus? = null

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is PutRoomRequest) return false
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
        if (if (`this$courtId` == null) `other$courtId` != null else `this$courtId` != `other$courtId`) return false
        val `this$status`: Any? = status
        val `other$status`: Any? = other.status
        return if (if (`this$status` == null) `other$status` != null else `this$status` != `other$status`) false else true
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is PutRoomRequest
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
        val `$status`: Any? = status
        result = result * PRIME + (`$status`?.hashCode() ?: 43)
        return result
    }

    override fun toString(): String {
        return "PutRoomRequest(startTime=" + startTime + ", endTime=" + endTime + ", name=" + name + ", courtId=" + courtId + ", status=" + status + ")"
    }
}