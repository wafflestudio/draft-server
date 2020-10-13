package com.wafflestudio.draft.service

import com.wafflestudio.draft.dto.response.ParticipantsResponse
import com.wafflestudio.draft.dto.response.UserInformationResponse
import com.wafflestudio.draft.model.Participant
import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.User
import com.wafflestudio.draft.model.enums.Team
import com.wafflestudio.draft.repository.ParticipantRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ParticipantService(private val participantRepository: ParticipantRepository) {
    fun getParticipants(room: Room?): ParticipantsResponse {
        val participantsOfTeam1: MutableList<UserInformationResponse>? = participantRepository.getUsersInTeam(room, Team.A)
        val participantsOfTeam2: MutableList<UserInformationResponse>? = participantRepository.getUsersInTeam(room, Team.B)
        return ParticipantsResponse(participantsOfTeam1, participantsOfTeam2)
    }

    fun addParticipants(room: Room, user: User): ParticipantsResponse {
        val participantsOfTeam1 = participantRepository.getUsersInTeam(room, Team.A)
        val participantsOfTeam2 = participantRepository.getUsersInTeam(room, Team.B)
        var teamOfNewParticipant = Team.A
        if (participantsOfTeam1!!.size > participantsOfTeam2!!.size) {
            teamOfNewParticipant = Team.B
            participantsOfTeam2.add(UserInformationResponse(user))
        } else {
            participantsOfTeam1.add(UserInformationResponse(user))
        }
        val newParticipant = Participant(user, room, teamOfNewParticipant)
        participantRepository.save(newParticipant)
        return ParticipantsResponse(participantsOfTeam1, participantsOfTeam2)
    }

    fun deleteParticipants(room: Room, user: User) {
        participantRepository.deleteParticipantByRoomAndUser(room, user)
    }
}
