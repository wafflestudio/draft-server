package com.wafflestudio.draft.service

import com.wafflestudio.draft.dto.response.ParticipantsResponse
import com.wafflestudio.draft.dto.response.UserInformationResponse
import com.wafflestudio.draft.model.Participant
import com.wafflestudio.draft.model.Room
import com.wafflestudio.draft.model.User
import com.wafflestudio.draft.repository.ParticipantRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ParticipantService(private val participantRepository: ParticipantRepository) {
    fun getParticipants(room: Room?): ParticipantsResponse {
        val participantsOfTeam1 = participantRepository.getUsersInTeam(room, 1)
        val participantsOfTeam2 = participantRepository.getUsersInTeam(room, 2)
        return ParticipantsResponse(participantsOfTeam1!!, participantsOfTeam2!!)
    }

    fun addParticipants(room: Room, user: User): ParticipantsResponse {
        val participantsOfTeam1 = participantRepository.getUsersInTeam(room, 1)
        val participantsOfTeam2 = participantRepository.getUsersInTeam(room, 2)
        var teamOfNewParticipant = 1
        if (participantsOfTeam1!!.size > participantsOfTeam2!!.size) {
            teamOfNewParticipant = 2
            participantsOfTeam2.add(UserInformationResponse(user))
        } else {
            participantsOfTeam1.add(UserInformationResponse(user))
        }
        val newParticipant = Participant(user, room, teamOfNewParticipant)
        participantRepository.save(newParticipant)
        return ParticipantsResponse(participantsOfTeam1, participantsOfTeam2)
    }

}