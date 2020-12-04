package com.wafflestudio.draft.repository

import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.User
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.persistence.EntityManager

@Repository
class RoomRepository(private val em: EntityManager) {
    fun save(room: Room?) {
        em.persist(room)
    }

    fun findOne(id: Long?): Room {
        return em.find(Room::class.java, id)
    }

    fun findRooms(name: String?, regionId: Long?, startTime: LocalDateTime?, endTime: LocalDateTime?): List<Room>? {
        return if (regionId == null) {
            em.createQuery("SELECT r FROM Room r " +
                    "WHERE r.name LIKE '%'||:name||'%' " +
                    "AND (COALESCE(:start_time, null) is null or r.startTime >= :start_time) " +
                    "AND (COALESCE(:end_time, null) is null or r.endTime <= :end_time) "
                    , Room::class.java)
                    .setParameter("name", name)
                    .setParameter("start_time", startTime)
                    .setParameter("end_time", endTime)
                    .resultList
        } else {
            em.createQuery("SELECT r FROM Room r " +
                    "INNER JOIN r.court c " +
                    "INNER JOIN c.region reg " +
                    "WHERE r.name LIKE '%'||:name||'%' " +
                    "AND (COALESCE(:start_time, null) is null or r.startTime >= :start_time) " +
                    "AND (COALESCE(:end_time, null) is null or r.endTime <= :end_time) " +
                    "AND reg.id = :region_id "
                    , Room::class.java)
                    .setParameter("name", name)
                    .setParameter("start_time", startTime)
                    .setParameter("end_time", endTime)
                    .setParameter("region_id", regionId)
                    .resultList
        }
    }

    fun findRoomsByUser(user: User?): MutableList<Room>? {
        return em.createQuery("SELECT r FROM Participant p " +
                "INNER JOIN p.room r " +
                "WHERE p.user = :user"
                , Room::class.java)
                .setParameter("user", user)
                .resultList
    }
}
