package com.wafflestudio.draft.dto

import com.wafflestudio.draft.model.enums.Team
import javax.validation.constraints.Min
import javax.validation.constraints.Pattern

class GameDTO {
    data class CreateRequest(
            val elapsedTime: Int? = null,
            @Pattern(regexp = "[0-9]+:[0-9]+")
            val gameScore: String? = null
    )
    data class Response(
            val elapsedTime: Int? = null,
            val winningTeam: Team = Team.NONE,
            val gameScore: String? = null
    )
}