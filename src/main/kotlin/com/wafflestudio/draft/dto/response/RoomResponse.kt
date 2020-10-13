package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.enums.RoomStatus
import java.time.LocalDateTime

class RoomResponse {
    var id: Long? = null
    var roomStatus: RoomStatus? = null
    var startTime: LocalDateTime? = null
    var endTime: LocalDateTime? = null
    var name: String? = null
    var createdAt: LocalDateTime? = null
    var ownerId: Long? = null
    var courtId: Long? = null

    constructor(room: Room?) {
        id = room!!.id
        roomStatus = room.status
        startTime = room.startTime
        endTime = room.endTime
        name = room.name
        createdAt = room.createdAt
        ownerId = room.owner!!.id
        courtId = room.court!!.id
    }

    constructor() {}

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is RoomResponse) return false
        val other = o
        if (!other.canEqual(this as Any)) return false
        val `this$id`: Any? = id
        val `other$id`: Any? = other.id
        if (if (`this$id` == null) `other$id` != null else `this$id` != `other$id`) return false
        val `this$roomStatus`: Any? = roomStatus
        val `other$roomStatus`: Any? = other.roomStatus
        if (if (`this$roomStatus` == null) `other$roomStatus` != null else `this$roomStatus` != `other$roomStatus`) return false
        val `this$startTime`: Any? = startTime
        val `other$startTime`: Any? = other.startTime
        if (if (`this$startTime` == null) `other$startTime` != null else `this$startTime` != `other$startTime`) return false
        val `this$endTime`: Any? = endTime
        val `other$endTime`: Any? = other.endTime
        if (if (`this$endTime` == null) `other$endTime` != null else `this$endTime` != `other$endTime`) return false
        val `this$name`: Any? = name
        val `other$name`: Any? = other.name
        if (if (`this$name` == null) `other$name` != null else `this$name` != `other$name`) return false
        val `this$createdAt`: Any? = createdAt
        val `other$createdAt`: Any? = other.createdAt
        if (if (`this$createdAt` == null) `other$createdAt` != null else `this$createdAt` != `other$createdAt`) return false
        val `this$ownerId`: Any? = ownerId
        val `other$ownerId`: Any? = other.ownerId
        if (if (`this$ownerId` == null) `other$ownerId` != null else `this$ownerId` != `other$ownerId`) return false
        val `this$courtId`: Any? = courtId
        val `other$courtId`: Any? = other.courtId
        return if (if (`this$courtId` == null) `other$courtId` != null else `this$courtId` != `other$courtId`) false else true
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is RoomResponse
    }

    override fun hashCode(): Int {
        val PRIME = 59
        var result = 1
        val `$id`: Any? = id
        result = result * PRIME + (`$id`?.hashCode() ?: 43)
        val `$roomStatus`: Any? = roomStatus
        result = result * PRIME + (`$roomStatus`?.hashCode() ?: 43)
        val `$startTime`: Any? = startTime
        result = result * PRIME + (`$startTime`?.hashCode() ?: 43)
        val `$endTime`: Any? = endTime
        result = result * PRIME + (`$endTime`?.hashCode() ?: 43)
        val `$name`: Any? = name
        result = result * PRIME + (`$name`?.hashCode() ?: 43)
        val `$createdAt`: Any? = createdAt
        result = result * PRIME + (`$createdAt`?.hashCode() ?: 43)
        val `$ownerId`: Any? = ownerId
        result = result * PRIME + (`$ownerId`?.hashCode() ?: 43)
        val `$courtId`: Any? = courtId
        result = result * PRIME + (`$courtId`?.hashCode() ?: 43)
        return result
    }

    override fun toString(): String {
        return "RoomResponse(id=" + id + ", roomStatus=" + roomStatus + ", startTime=" + startTime + ", endTime=" + endTime + ", name=" + name + ", createdAt=" + createdAt + ", ownerId=" + ownerId + ", courtId=" + courtId + ")"
    }
}