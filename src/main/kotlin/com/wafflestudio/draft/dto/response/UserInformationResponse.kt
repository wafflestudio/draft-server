package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.User

data class UserInformationResponse (
    var id: Long,
    var username: String,
    var email: String,
    var profileImage: String
){
    constructor(user:User){
        id= user.id!!
        username=user.username
        email=user.email
        profileImage=user.profileImage
    }
}