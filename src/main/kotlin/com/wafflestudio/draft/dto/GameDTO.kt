package com.wafflestudio.draft.dto

import com.wafflestudio.draft.model.enums.Team

class GameDTO {
    data class CreateRequest(
            val elapsedTime: Int? = null,
            val teamAScore: Int = 0,
            val teamBScore: Int = 0
    )
    data class Response(
            val elapsedTime: Int? = null,
            val winningTeam: Team = Team.NONE,
            val teamAScore: Int? = 0,
            val teamBScore: Int? = 0
    )
}
