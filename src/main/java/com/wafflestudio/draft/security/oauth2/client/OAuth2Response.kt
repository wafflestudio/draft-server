package com.wafflestudio.draft.security.oauth2.client

import org.springframework.http.HttpStatus

class OAuth2Response(private var authServer: String, private var email: String, private var status: HttpStatus) {
    fun getAuthServer(): String {
        return authServer
    }

    fun getEmail(): String {
        return email
    }

    fun getStatus(): HttpStatus {
        return status
    }

    fun setAuthServer(authServer: String) {
        this.authServer = authServer
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun setStatus(status: HttpStatus) {
        this.status = status
    }

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is OAuth2Response) return false
        val other = o
        if (!other.canEqual(this as Any)) return false
        val `this$authServer`: Any = getAuthServer()
        val `other$authServer`: Any = other.getAuthServer()
        if (if (`this$authServer` == null) `other$authServer` != null else `this$authServer` != `other$authServer`) return false
        val `this$email`: Any = getEmail()
        val `other$email`: Any = other.getEmail()
        if (if (`this$email` == null) `other$email` != null else `this$email` != `other$email`) return false
        val `this$status`: Any = getStatus()
        val `other$status`: Any = other.getStatus()
        return if (if (`this$status` == null) `other$status` != null else `this$status` != `other$status`) false else true
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is OAuth2Response
    }

    override fun hashCode(): Int {
        val PRIME = 59
        var result = 1
        val `$authServer`: Any = getAuthServer()
        result = result * PRIME + (`$authServer`?.hashCode() ?: 43)
        val `$email`: Any = getEmail()
        result = result * PRIME + (`$email`?.hashCode() ?: 43)
        val `$status`: Any = getStatus()
        result = result * PRIME + (`$status`?.hashCode() ?: 43)
        return result
    }

    override fun toString(): String {
        return "OAuth2Response(authServer=" + getAuthServer() + ", email=" + getEmail() + ", status=" + getStatus() + ")"
    }

}