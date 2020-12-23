package com.wafflestudio.draft.service

import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.User
import com.wafflestudio.draft.error.RoomNotFoundException
import com.wafflestudio.draft.error.WrongRoomTimeException
import com.wafflestudio.draft.repository.RoomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class RoomService(private val roomRepository: RoomRepository) {
    @Transactional
    fun save(room: Room): Long {
        val startTimeExists = room.startTime != null
        val endTimeExists = room.endTime != null
        if ((startTimeExists xor endTimeExists)
                || (startTimeExists && endTimeExists && room.startTime!! >= room.endTime)) {
            throw WrongRoomTimeException()
        }
        roomRepository.save(room)
        return room.id!!
    }

    fun findRooms(name: String?, regionId: Long?, startTime: LocalDateTime?, endTime: LocalDateTime?): List<Room>? {
        if (regionId == null) {
            return roomRepository.findByNameAndOptionalStartTimeAndOptionalEndTime(name, startTime, endTime)
        }
        return roomRepository.findByNameAndOptionalRegionIdAndOptionalStartTimeAndOptionalEndTime(
                name, regionId, startTime, endTime
        )
    }

    fun findOne(roomId: Long): Room {
        return roomRepository.findById(roomId).orElseThrow(::RoomNotFoundException)!!
    }

    fun findRoomsByUser(user: User): List<Room>? {
        return roomRepository.findByParticipatingUser(user)
    }

    fun existsCurrentlyParticipating(user: User, startTime: LocalDateTime, endTime: LocalDateTime): Boolean {
        return roomRepository.existsByParticipatingUserAndStartTimeAndEndTime(user, startTime, endTime)
    }

    fun existsCurrentlyParticipatingExcludingRoom(
            user: User, startTime: LocalDateTime, endTime: LocalDateTime, thisRoom: Room
    ): Boolean {
        return roomRepository.existsByParticipatingUserAndStartTimeAndEndTimeAndRoomNot(user, startTime, endTime, thisRoom)
    }
}
