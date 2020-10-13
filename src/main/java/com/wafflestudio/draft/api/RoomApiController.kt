package com.wafflestudio.draft.api

import com.wafflestudio.draft.dto.request.CreateRoomRequest
import com.wafflestudio.draft.dto.request.GetRoomsRequest
import com.wafflestudio.draft.dto.request.PutRoomRequest
import com.wafflestudio.draft.dto.response.ParticipantsResponse
import com.wafflestudio.draft.dto.response.RoomResponse
import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.User
import com.wafflestudio.draft.security.CurrentUser
import com.wafflestudio.draft.service.CourtService
import com.wafflestudio.draft.service.FCMService
import com.wafflestudio.draft.service.ParticipantService
import com.wafflestudio.draft.service.RoomService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/v1/room")
class RoomApiController(private val fcmService: FCMService, // FIXME: Use fcmService.send(message) when room create
                        private val participantService: ParticipantService, private val roomService: RoomService, private val courtService: CourtService) {

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveRoomV1(@RequestBody request: CreateRoomRequest, @CurrentUser currentUser: User?): RoomResponse {
        val room = Room()
        room.owner = currentUser
        room.startTime = request.startTime
        room.endTime = request.endTime
        room.name = request.name
        val court = courtService.getCourtById(request.courtId)
        if (court!!.isEmpty) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
        room.court = court.get()
        roomService.save(room)
        return RoomResponse(room)
    }

    @GetMapping(path = ["{id}"])
    fun getRoomV1(@PathVariable("id") id: Long?): RoomResponse {
        val room = roomService.findOne(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return RoomResponse(room)
    }

    @GetMapping("/")
    fun getRoomsV1(@ModelAttribute request: GetRoomsRequest): List<RoomResponse> {
        var name = request.name
        if (name == null) {
            name = ""
        }
        val courtId = request.courtId
        // FIXME: startTime and endTime for getting rooms will be used later
        val startTime = request.startTime
        val endTime = request.endTime
        val rooms = roomService.findRooms(name, courtId, startTime, endTime)
        val getRoomsResponse: MutableList<RoomResponse> = ArrayList()
        for (room in rooms!!) {
            getRoomsResponse.add(RoomResponse(room))
        }
        return getRoomsResponse
    }

    @PostMapping(path = ["{id}/participant"])
    fun participate(@PathVariable("id") id: Long?, @CurrentUser currentUser: User): ParticipantsResponse? {
        val room = roomService.findOne(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return participantService.addParticipants(room, currentUser)
    }

    @GetMapping(path = ["{id}/participant"])
    fun getParticipants(@PathVariable("id") id: Long?): ParticipantsResponse? {
        val room = roomService.findOne(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return participantService.getParticipants(room)
    }

    @PutMapping(path = ["{id}"])
    fun putRoomV1(@PathVariable("id") id: Long?, @RequestBody request: PutRoomRequest): RoomResponse {
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