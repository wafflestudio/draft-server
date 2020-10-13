package com.wafflestudio.draft.dto.request

class SignUpRequest : AuthenticationRequest() {
    var username: String? = null

    override fun toString(): String {
        return "SignUpRequest(username=" + username + ")"
    }

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is SignUpRequest) return false
        val other = o
        if (!other.canEqual(this as Any)) return false
        if (!super.equals(o)) return false
        val `this$username`: Any? = username
        val `other$username`: Any? = other.username
        return if (if (`this$username` == null) `other$username` != null else `this$username` != `other$username`) false else true
    }

    override fun canEqual(other: Any?): Boolean {
        return other is SignUpRequest
    }

    override fun hashCode(): Int {
        val PRIME = 59
        var result = super.hashCode()
        val `$username`: Any? = username
        result = result * PRIME + (`$username`?.hashCode() ?: 43)
        return result
    } // TODO: More information about region, preference... should be added
}