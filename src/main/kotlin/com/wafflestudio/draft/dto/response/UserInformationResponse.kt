package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.User

class UserInformationResponse {
    var id: Long
    var username: String
    var email: String

    constructor(user: User) {
        id = user.id!!
        username = user.username
        email = user.email
    }

    constructor(id: Long, username: String, email: String) {
        this.id = id
        this.username = username
        this.email = email
    }

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is UserInformationResponse) return false
        val other = o
        if (!other.canEqual(this as Any)) return false
        val `this$id`: Any = id
        val `other$id`: Any = other.id
        if (if (`this$id` == null) `other$id` != null else `this$id` != `other$id`) return false
        val `this$username`: Any = username
        val `other$username`: Any = other.username
        if (if (`this$username` == null) `other$username` != null else `this$username` != `other$username`) return false
        val `this$email`: Any = email
        val `other$email`: Any = other.email
        return if (if (`this$email` == null) `other$email` != null else `this$email` != `other$email`) false else true
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is UserInformationResponse
    }

    override fun hashCode(): Int {
        val PRIME = 59
        var result = 1
        val `$id`: Any = id
        result = result * PRIME + (`$id`?.hashCode() ?: 43)
        val `$username`: Any = username
        result = result * PRIME + (`$username`?.hashCode() ?: 43)
        val `$email`: Any = email
        result = result * PRIME + (`$email`?.hashCode() ?: 43)
        return result
    }

    override fun toString(): String {
        return "UserInformationResponse(id=" + id + ", username=" + username + ", email=" + email + ")"
    }
}