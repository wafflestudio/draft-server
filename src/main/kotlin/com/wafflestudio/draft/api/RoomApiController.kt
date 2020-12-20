package com.wafflestudio.draft.api

import com.wafflestudio.draft.dto.request.CreateRoomRequest
import com.wafflestudio.draft.dto.request.PutRoomRequest
import com.wafflestudio.draft.dto.response.*
import com.wafflestudio.draft.error.*
import com.wafflestudio.draft.model.Participant
import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.enums.RoomStatus
import com.wafflestudio.draft.security.CurrentUser
import com.wafflestudio.draft.security.password.UserPrincipal
import com.wafflestudio.draft.service.*
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
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
        if (roomService.existsCurrentlyParticipating(currentUser.user, request.startTime, request.endTime)) {
            throw ConcurrentlyParticipatingOtherRoomException()
        }

        val room = Room()
        room.owner = currentUser.user
        room.startTime = request.startTime
        room.endTime = request.endTime
        room.name = request.name

        val court = courtService.getCourtById(request.courtId)
        room.court = court
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
        val room = roomService.findOne(id)
        return RoomResponse(room)
    }

    @PostMapping(path = ["{id}/participant"])
    fun participate(@PathVariable("id") id: Long, @CurrentUser currentUser: UserPrincipal): RoomResponse {
        val room: Room = roomService.findOne(id)
        if (room.status !== RoomStatus.WAITING) {
            throw RoomIsNotWaitingException()
        }

        val participants: List<Participant> = room.participants
        participants.forEach { participant ->
            if (currentUser.user.id === participant.user.id) {
                throw AlreadyParticipatingRoomException()
            }
        }

        if (participants.size >= room.court!!.capacity!!) {
            throw RoomIsFullException()
        }

        if (roomService.existsCurrentlyParticipating(currentUser.user, room.startTime!!, room.endTime!!)) {
            throw ConcurrentlyParticipatingOtherRoomException()
        }

        participantService.addParticipants(room, currentUser.user)
        return RoomResponse(room)
    }

    @GetMapping(path = ["{id}/participant/"])
    fun getParticipants(@PathVariable("id") id: Long): ParticipantsResponse? {
        val room = roomService.findOne(id)
        return participantService.getParticipants(room)
    }

    @DeleteMapping(path = ["{id}/participant/"])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun leaveRoom(@PathVariable("id") id: Long, @CurrentUser currentUser: UserPrincipal) {
        val room: Room = roomService.findOne(id)
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
    fun putRoomV1(@PathVariable("id") id: Long,
                  @RequestBody request: PutRoomRequest,
                  @CurrentUser currentUser: UserPrincipal): RoomResponse {
        val room = roomService.findOne(id)
        request.startTime?.let{ room.startTime = it }
        request.endTime?.let{ room.endTime = it }
        request.name?.let{ room.name = it }
        request.status?.let { room.status = it }

        if(room.startTime != null && room.endTime != null
                && roomService.existsCurrentlyParticipatingExcludingRoom(
                        currentUser.user, room.startTime!!, room.endTime!!, room
                )) {
            throw ConcurrentlyParticipatingOtherRoomException()
        }

        roomService.save(room)
        return RoomResponse(room)
    }
}
