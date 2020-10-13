package com.wafflestudio.draft.repository

import com.wafflestudio.draft.dto.response.UserInformationResponse
import com.wafflestudio.draft.model.Participant
import com.wafflestudio.draft.model.Room
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ParticipantRepository : JpaRepository<Participant?, Long?> {
    fun getAllByRoom(room: Room?): List<Participant?>?

    @Query("SELECT new com.wafflestudio.draft.dto.response.UserInformationResponse(u.id,u.username,u.email) " +
            "from Participant p " +
            "INNER JOIN p.user u " +
            "WHERE p.room = :room " +
            "AND p.team = :team")
    fun getUsersInTeam(
            @Param("room") room: Room?,
            @Param("team") team: Int): MutableList<UserInformationResponse?>?
}