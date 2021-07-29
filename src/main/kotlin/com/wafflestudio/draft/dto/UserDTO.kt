package com.wafflestudio.draft.dto

//import com.wafflestudio.draft.dto.response.UserInformationResponse
import com.wafflestudio.draft.model.User
import javax.validation.constraints.NotNull

class UserDTO {
    data class UserInformationResponse(
        var id: Long,
        var username: String,
        var email: String,
        var profileImage: String
    ) {
        constructor(user: User) : this(
            user.id!!,
            user.username,
            user.email,
            user.profileImage!!
        )
    }

    data class ParticipantsResponse(
        var team1: List<UserInformationResponse>?,
        var team2: List<UserInformationResponse>?
    )

    data class CheckUsernameRequest(
        @field:NotNull
        val username: String
    )
}