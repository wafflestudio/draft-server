package com.wafflestudio.draft.repository;

import com.wafflestudio.draft.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
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

    public List<Room> findAll(String name, LocalDateTime startTime, LocalDateTime endTime) {
        return em.createQuery("SELECT r FROM Room r " +
                              "WHERE r.name LIKE concat('%', :name, '%') ", Room.class)
                .setParameter("name", name)
                .getResultList();
    }
}
