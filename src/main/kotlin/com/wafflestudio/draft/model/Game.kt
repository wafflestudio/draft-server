package com.wafflestudio.draft.model

import com.wafflestudio.draft.dto.GameDTO
import com.wafflestudio.draft.model.enums.Team
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Min

@Entity
class Game(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Min(value = 0, message = "The value must be positive.")
        val elapsedTime: Int? = null,

        val winningTeam: Team = Team.NONE,
        val teamAScore: Int = 0,
        val teamBScore: Int = 0
) : BaseTimeEntity() {
    fun toResponse(): GameDTO.Response {
        return GameDTO.Response(elapsedTime, winningTeam, teamAScore, teamBScore)
    }
}
