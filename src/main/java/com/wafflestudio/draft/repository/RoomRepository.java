package com.wafflestudio.draft.repository;

import com.wafflestudio.draft.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomRepository {

    private final EntityManager em;

    public void save(Room room) {
        em.persist(room);
    }

    public Room findOne(Long id) {
        return em.find(Room.class, id);
    }

    public List<Room> findAll() {
        return em.createQuery("SELECT r FROM Room r", Room.class)
                .getResultList();
    }

    public List<Room> findRooms(String name, Long courtId, LocalDateTime startTime, LocalDateTime endTime) {
        // FIXME: we should find a smarter way...
        if (courtId == null) {
            return em.createQuery("SELECT r FROM Room r " +
                            "INNER JOIN r.court c " +
                            "WHERE r.name LIKE '%'||:name||'%' "
                    , Room.class)
                    .setParameter("name", name)
                    .getResultList();
        }
        else {
            return em.createQuery("SELECT r FROM Room r " +
                            "INNER JOIN r.court c " +
                            "WHERE r.name LIKE '%'||:name||'%' " +
                            "AND c.id = :court_id"
                    , Room.class)
                    .setParameter("name", name)
                    .setParameter("court_id", courtId)
                    .getResultList();
        }
    }
}
