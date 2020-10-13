package com.wafflestudio.draft.dto.request

import javax.validation.constraints.NotNull

data class CheckUsernameRequest(
        @NotNull
        val username: String
)
