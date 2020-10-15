package com.wafflestudio.draft.service

import com.wafflestudio.draft.dto.response.RoomResponse
import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.User
import com.wafflestudio.draft.repository.RoomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class RoomService(private val roomRepository: RoomRepository, private val participantService: ParticipantService) {
    @Transactional
    fun save(room: Room): Long? {
        roomRepository.save(room)
        return room.id
    }

    fun findRooms(name: String?, courtId: Long?, startTime: LocalDateTime?, endTime: LocalDateTime?): List<Room>? {
        return roomRepository.findRooms(name, courtId, startTime, endTime)
    }

    fun findOne(roomId: Long): Room? {
        return roomRepository.findOne(roomId)
    }


    fun findRoomsByUser(user: User): List<Room>? {
        return roomRepository.findRoomsByUser(user)
    }

    fun makeRoomResponse(room: Room): RoomResponse {
        val roomResponse = RoomResponse(room)
        roomResponse.participants = (participantService.getParticipants(room))
        return roomResponse
    }
}