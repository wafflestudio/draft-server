package com.wafflestudio.draft.service;

import com.wafflestudio.draft.dto.response.ParticipantsResponse;
import com.wafflestudio.draft.dto.response.UserInformationResponse;
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

    public ParticipantsResponse addParticipants(Room room, User user) {
        List<UserInformationResponse> participantsOfTeam1 = participantRepository.getUsersInTeam(room, 1);
        List<UserInformationResponse> participantsOfTeam2 = participantRepository.getUsersInTeam(room, 2);
        int teamOfNewParticipant = 1;
        if (participantsOfTeam1.size() > participantsOfTeam2.size()) {
            teamOfNewParticipant = 2;
            participantsOfTeam2.add(new UserInformationResponse(user.getId(), user.getUsername(), user.getEmail()));
        } else {
            participantsOfTeam1.add(new UserInformationResponse(user.getId(), user.getUsername(), user.getEmail()));
        }
        Participant newParticipant = new Participant(user, room, teamOfNewParticipant);
        participantRepository.save(newParticipant);
        return new ParticipantsResponse(participantsOfTeam1, participantsOfTeam2);
    }
}
