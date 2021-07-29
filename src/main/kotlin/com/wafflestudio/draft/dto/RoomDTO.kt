package com.wafflestudio.draft.dto

//import com.wafflestudio.draft.dto.response.UserInformationResponse
import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.User
import com.wafflestudio.draft.model.enums.RoomStatus
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class RoomDTO {
    data class CreateRequest(
            @field:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            var startTime: LocalDateTime,
            @field:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            var endTime: LocalDateTime,
            var name: String,
            var courtId: Long
    )

    data class UpdateRequest(
            @field:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            val startTime: LocalDateTime? = null,
            @field:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            val endTime: LocalDateTime? = null,
            val name: String? = null,
            val courtId: Long? = null,
            val status: RoomStatus? = null
    )

    data class Summary(
            var id: Long? = null,
            var roomStatus: RoomStatus? = null,
            var startTime: LocalDateTime? = null,
            var endTime: LocalDateTime? = null,
            var name: String? = null,
            var createdAt: LocalDateTime? = null,
            var ownerId: Long? = null,
            var courtId: Long? = null,
            var participants: MutableMap<Long, UserDTO.UserInformationResponse> = mutableMapOf()
    ) {
        fun toResponse(): Response {
            return Response(id, roomStatus, startTime, endTime, name, createdAt, ownerId, courtId, participants.values.toList())
        }
    }

    data class Response(
            var id: Long? = null,
            var roomStatus: RoomStatus? = null,
            var startTime: LocalDateTime? = null,
            var endTime: LocalDateTime? = null,
            var name: String? = null,
            var createdAt: LocalDateTime? = null,
            var ownerId: Long? = null,
            var courtId: Long? = null,
            var participants: List<UserDTO.UserInformationResponse>? = null
    ) {
        constructor(room: Room) : this() {
            this.id = room.id
            this.roomStatus = room.status
            this.startTime = room.startTime
            this.endTime = room.endTime
            this.name = room.name
            this.createdAt = room.createdAt
            this.ownerId = room.owner!!.id
            this.courtId = room.court!!.id
            val userResponses: MutableList<UserDTO.UserInformationResponse> = mutableListOf()
            room.participants.map { userResponses.add(UserDTO.UserInformationResponse(it.user)) }
            this.participants = userResponses
        }
    }

    data class RoomsOfUserResponse(
        var id: Long? = null,
        var username: String? = null,
        var email: String? = null,
        var rooms: List<Response>? = null
    ) {
        constructor(user: User) : this(
            user.id,
            user.username,
            user.email
        )
    }


}