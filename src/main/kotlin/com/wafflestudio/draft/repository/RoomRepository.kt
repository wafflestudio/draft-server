package com.wafflestudio.draft.repository

import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface RoomRepository : JpaRepository<Room?, Long?> {

    @Query("SELECT r FROM Room r " +
            "INNER JOIN r.court c " +
            "WHERE r.name LIKE %:name% " +
            "AND (COALESCE(:start_time, null) is null or r.startTime >= :start_time) " +
            "AND (COALESCE(:end_time, null) is null or r.endTime <= :end_time) " +
            "AND c.region.id = :region_id ")
    fun findByNameAndOptionalRegionIdAndOptionalStartTimeAndOptionalEndTime(
            @Param("name") name: String?,
            @Param("region_id") regionId: Long?,
            @Param("start_time") startTime: LocalDateTime?,
            @Param("end_time") endTime: LocalDateTime?
    ): List<Room>?

    @Query("SELECT r FROM Room r " +
            "WHERE r.name LIKE %:name% " +
            "AND (COALESCE(:start_time, null) is null or r.startTime >= :start_time) " +
            "AND (COALESCE(:end_time, null) is null or r.endTime <= :end_time)")
    fun findByNameAndOptionalStartTimeAndOptionalEndTime(
            @Param("name") name: String?,
            @Param("start_time") startTime: LocalDateTime?,
            @Param("end_time") endTime: LocalDateTime?
    ): List<Room>?

    @Query("SELECT r FROM Participant p INNER JOIN p.room r WHERE p.user = :participatingUser")
    fun findByParticipatingUser(participatingUser: User): List<Room>?

    @Query("SELECT COUNT(r.id) > 0 FROM Participant p INNER JOIN p.room r " +
            "WHERE p.user = :participating_user " +
            "AND ((r.endTime > :start_time AND r.endTime < :end_time) " +
            "OR (r.startTime < :start_time AND r.startTime > :end_time) " +
            "OR (r.startTime >= :start_time AND r.endTime <= :end_time) " +
            "OR (r.startTime <= :start_time AND r.endTime >= :end_time))")
    fun existsByParticipatingUserAndStartTimeAndEndTime(
            @Param("participating_user") participatingUser: User,
            @Param("start_time") startTime: LocalDateTime,
            @Param("end_time") endTime: LocalDateTime
    ): Boolean

    @Query("SELECT COUNT(r.id) > 0 FROM Participant p INNER JOIN p.room r " +
            "WHERE p.user = :participating_user " +
            "AND r != :this_room " +
            "AND ((r.endTime > :start_time AND r.endTime < :end_time) " +
            "OR (r.startTime < :start_time AND r.startTime > :end_time) " +
            "OR (r.startTime >= :start_time AND r.endTime <= :end_time) " +
            "OR (r.startTime <= :start_time AND r.endTime >= :end_time))")
    fun existsByParticipatingUserAndStartTimeAndEndTimeAndRoomNot(
            @Param("participating_user") participatingUser: User,
            @Param("start_time") startTime: LocalDateTime,
            @Param("end_time") endTime: LocalDateTime,
            @Param("this_room") thisRoom: Room
    ): Boolean

    fun findRoomById(roomId: Long): Room?

}
