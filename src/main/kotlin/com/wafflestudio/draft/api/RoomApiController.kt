package com.wafflestudio.draft.api

import com.wafflestudio.draft.dto.request.CreateRoomRequest
import com.wafflestudio.draft.dto.request.PutRoomRequest
import com.wafflestudio.draft.dto.response.*
import com.wafflestudio.draft.model.Participant
import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.enums.RoomStatus
import com.wafflestudio.draft.security.CurrentUser
import com.wafflestudio.draft.security.password.UserPrincipal
import com.wafflestudio.draft.service.*
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/room")
class RoomApiController(private val fcmService: FCMService, // FIXME: Use fcmService.send(message) when room create
                        private val courtService: CourtService, private val participantService: ParticipantService,
                        private val roomService: RoomService) {

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveRoomV1(@RequestBody @Valid request: CreateRoomRequest, @CurrentUser currentUser: UserPrincipal): RoomResponse {
        val room = Room()
        room.owner = currentUser.user
        room.startTime = request.startTime
        room.endTime = request.endTime
        room.name = request.name
        val court = courtService.getCourtById(request.courtId)
        if (court.isEmpty) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
        room.court = court.get()
        roomService.save(room)
        participantService.addParticipants(room, currentUser.user)
        return RoomResponse(room)
    }

    @GetMapping("/")
    fun getRoomsV1(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") startTime: LocalDateTime?,
                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") endTime: LocalDateTime?,
                   @RequestParam name: String?, @RequestParam regionId: Long?
                   ): ListResponse<RoomResponse> {
        val rooms = roomService.findRooms(name.orEmpty(), regionId, startTime, endTime)
        return  ListResponse(rooms!!.map { RoomResponse(it) })
    }

    @GetMapping(path = ["{id}"])
    fun getRoomV1(@PathVariable("id") id: Long): RoomResponse {
        val room = roomService.findOne(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return RoomResponse(room)
    }

    @PostMapping(path = ["{id}/participant"])
    fun participate(@PathVariable("id") id: Long, @CurrentUser currentUser: UserPrincipal): ParticipantsResponse? {
        val room: Room = roomService.findOne(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        if (room.status !== RoomStatus.WAITING) {
            throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY)
        }
        val participants: List<Participant> = room.participants
        participants.forEach { participant ->
            if (currentUser.user.id === participant.user.id) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST)
            }
        }

        if (participants.size >= room.court!!.capacity!!) {
            throw ResponseStatusException(HttpStatus.CONFLICT)
        }

        return participantService.addParticipants(room, currentUser.user)
    }

    @GetMapping(path = ["{id}/participant/"])
    fun getParticipants(@PathVariable("id") id: Long): ParticipantsResponse? {
        val room = roomService.findOne(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return participantService.getParticipants(room)
    }

    @DeleteMapping(path = ["{id}/participant/"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun leaveRoom(@PathVariable("id") id: Long, @CurrentUser currentUser: UserPrincipal) {
        val room: Room = roomService.findOne(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        participantService.deleteParticipants(room, currentUser.user)
        val participants: List<Participant>? = room.participants
        if (participants !== null && participants.isEmpty()) {
            room.status = RoomStatus.CLOSED
            room.owner = null
            roomService.save(room)
        }
        if (room.status !== RoomStatus.CLOSED) {
            if (room.owner!!.id === currentUser.user.id) {
                room.owner = participants!![0].user
                roomService.save(room)
            }
        }
    }

    @PutMapping(path = ["{id}"])
    fun putRoomV1(@PathVariable("id") id: Long, @RequestBody request: PutRoomRequest): RoomResponse {
        val room = roomService.findOne(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

        // FIXME: we should find a smarter way...
        val startTime = request.startTime
        if (startTime != null) {
            room.startTime = startTime
        }
        val endTime = request.endTime
        if (endTime != null) {
            room.endTime = endTime
        }
        val name = request.name
        if (name != null) {
            room.name = name
        }
        val status = request.status
        if (status != null) {
            room.status = status
        }
        roomService.save(room)
        return RoomResponse(room)
    }
}
