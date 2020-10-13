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

    fun findRooms(name: String?, courtId: Long?, startTime: LocalDateTime?, endTime: LocalDateTime?): List<Room> {
        // FIXME: we should find a smarter way...
        return if (courtId == null) {
            em.createQuery("SELECT r FROM Room r " +
                    "INNER JOIN r.court c " +
                    "WHERE r.name LIKE '%'||:name||'%' "
                    , Room::class.java)
                    .setParameter("name", name)
                    .resultList
        } else {
            em.createQuery("SELECT r FROM Room r " +
                    "INNER JOIN r.court c " +
                    "WHERE r.name LIKE '%'||:name||'%' " +
                    "AND c.id = :court_id"
                    , Room::class.java)
                    .setParameter("name", name)
                    .setParameter("court_id", courtId)
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
