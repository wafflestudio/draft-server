package com.wafflestudio.draft.api

import com.wafflestudio.draft.dto.GameDTO
import com.wafflestudio.draft.dto.RoomDTO
import com.wafflestudio.draft.dto.response.ListResponse
import com.wafflestudio.draft.dto.response.ParticipantsResponse
import com.wafflestudio.draft.error.AlreadyParticipatingRoomException
import com.wafflestudio.draft.error.ConcurrentlyParticipatingOtherRoomException
import com.wafflestudio.draft.error.RoomIsFullException
import com.wafflestudio.draft.error.RoomIsNotWaitingException
import com.wafflestudio.draft.model.Participant
import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.enums.RoomStatus
import com.wafflestudio.draft.security.CurrentUser
import com.wafflestudio.draft.security.password.UserPrincipal
import com.wafflestudio.draft.service.CourtService
import com.wafflestudio.draft.service.FCMService
import com.wafflestudio.draft.service.ParticipantService
import com.wafflestudio.draft.service.RoomService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/room")
class RoomApiController(private val fcmService: FCMService, // FIXME: Use fcmService.send(message) when room create
                        private val courtService: CourtService, private val participantService: ParticipantService,
                        private val roomService: RoomService, private val gameLogService: GameLogService) {

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveRoomV1(@RequestBody @Valid request: RoomDTO.CreateRequest, @CurrentUser currentUser: UserPrincipal): RoomDTO.Response {
        if (roomService.existsCurrentlyParticipating(currentUser.user, request.startTime, request.endTime)) {
            throw ConcurrentlyParticipatingOtherRoomException()
        }

        val room = Room()
        room.apply {
            owner = currentUser.user
            startTime = request.startTime
            endTime = request.endTime
            name = request.name
        }

        val court = courtService.getCourtById(request.courtId)
        room.court = court
        roomService.save(room)

        participantService.addParticipants(room, currentUser.user)
        return RoomDTO.Response(room)
    }

    @GetMapping("/")
    fun getRoomsV1(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") startTime: LocalDateTime?,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") endTime: LocalDateTime?,
            @RequestParam name: String?, @RequestParam regionId: Long?
    ): ListResponse<RoomDTO.Response> {
        val rooms = roomService.findRooms(name.orEmpty(), regionId, startTime, endTime)
        return ListResponse(rooms!!.map { it.toResponse() })
    }

    @GetMapping(path = ["{id}"])
    fun getRoomV1(@PathVariable("id") id: Long): RoomDTO.Response {
        val room = roomService.findOne(id)
        return RoomDTO.Response(room)
    }

    @PostMapping(path = ["{id}/participant"])
    fun participate(@PathVariable("id") id: Long, @CurrentUser currentUser: UserPrincipal): RoomDTO.Response {
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
        return RoomDTO.Response(room)
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
    fun putRoomV1(
            @PathVariable("id") id: Long,
            @RequestBody request: RoomDTO.UpdateRequest,
            @CurrentUser currentUser: UserPrincipal
    ): RoomDTO.Response {
        val room = roomService.findOne(id)
        room.apply {
            startTime = request.startTime ?: startTime
            endTime = request.endTime ?: endTime
            name = request.name ?: name
            status = request.status ?: status
        }

        if (room.startTime != null && room.endTime != null
                && roomService.existsCurrentlyParticipatingExcludingRoom(
                        currentUser.user, room.startTime!!, room.endTime!!, room
                )) {
            throw ConcurrentlyParticipatingOtherRoomException()
        }

        roomService.save(room)
        return RoomDTO.Response(room)
    }

    @PostMapping(path = ["{id}/result/"])
    fun addGameResult(
            @PathVariable("id") id: Long,
            @RequestBody @Valid request: GameDTO.CreateRequest
    ) {
        gameLogService.setGameResult(id,request)
    }
}
