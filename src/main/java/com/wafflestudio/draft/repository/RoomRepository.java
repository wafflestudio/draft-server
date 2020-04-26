package com.wafflestudio.draft.repository;

import com.wafflestudio.draft.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
