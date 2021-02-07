package com.wafflestudio.draft.dto.request

import javax.validation.constraints.NotNull

data class CheckUsernameRequest(
        @field:NotNull
        val username: String
)
