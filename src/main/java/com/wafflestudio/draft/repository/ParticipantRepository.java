package com.wafflestudio.draft.repository;

import com.wafflestudio.draft.dto.response.UserInformationResponse;
import com.wafflestudio.draft.model.Participant;
import com.wafflestudio.draft.model.Room;
import com.wafflestudio.draft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> getAllByRoom(Room room);

    @Query("SELECT new com.wafflestudio.draft.dto.response.UserInformationResponse(u.id,u.username,u.email) " +
            "from Participant p " +
            "INNER JOIN p.user u " +
            "WHERE p.room = :room " +
            "AND p.team = :team")
    List<UserInformationResponse> getUsersInTeam(
            @Param("room") Room room,
            @Param("team") int team);

    void deleteParticipantByRoomAndUser(Room room, User user);
}
