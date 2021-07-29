package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.dto.UserDTO

data class ParticipantsResponse(
        var team1: List<UserDTO.UserInformationResponse>?,
        var team2: List<UserDTO.UserInformationResponse>?
)
