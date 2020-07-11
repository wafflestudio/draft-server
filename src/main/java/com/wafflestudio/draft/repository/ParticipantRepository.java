package com.wafflestudio.draft.repository;

import com.wafflestudio.draft.model.Participant;
import com.wafflestudio.draft.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> getAllByRoom(Room room);

    List<Participant> findByRoomAndTeam(Room room, int team);


}
