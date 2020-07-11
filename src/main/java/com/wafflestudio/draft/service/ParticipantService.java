package com.wafflestudio.draft.service;

import com.wafflestudio.draft.model.Participant;
import com.wafflestudio.draft.model.Room;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    public List<Participant> getParticipants(Room room) {
        return participantRepository.getAllByRoom(room);
    }

    public void addParticipants(Room room, User user) {
        List<Participant> participantsOfTeam1 = participantRepository.findByRoomAndTeam(room, 1);
        List<Participant> participantsOfTeam2 = participantRepository.findByRoomAndTeam(room, 2);
        int teamOfNewParticipant = participantsOfTeam1.size() > participantsOfTeam2.size() ? 2 : 1;
        Participant newParticipant = new Participant(user, room, teamOfNewParticipant);
        participantRepository.save(newParticipant);
    }
}
