package com.wafflestudio.draft.dto.request

import org.springframework.lang.Nullable

open class AuthenticationRequest {
    var grantType: String? = null

    @get:Nullable
    @Nullable
    var accessToken: String? = null

    @get:Nullable
    @Nullable
    var authProvider: String? = null

    ///
    @get:Nullable
    @Nullable
    var email: String? = null

    @get:Nullable
    @Nullable
    var password: String? = null

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is AuthenticationRequest) return false
        val other = o
        if (!other.canEqual(this as Any)) return false
        val `this$grantType`: Any? = grantType
        val `other$grantType`: Any? = other.grantType
        if (if (`this$grantType` == null) `other$grantType` != null else `this$grantType` != `other$grantType`) return false
        val `this$accessToken`: Any? = accessToken
        val `other$accessToken`: Any? = other.accessToken
        if (if (`this$accessToken` == null) `other$accessToken` != null else `this$accessToken` != `other$accessToken`) return false
        val `this$authProvider`: Any? = authProvider
        val `other$authProvider`: Any? = other.authProvider
        if (if (`this$authProvider` == null) `other$authProvider` != null else `this$authProvider` != `other$authProvider`) return false
        val `this$email`: Any? = email
        val `other$email`: Any? = other.email
        if (if (`this$email` == null) `other$email` != null else `this$email` != `other$email`) return false
        val `this$password`: Any? = password
        val `other$password`: Any? = other.password
        return if (if (`this$password` == null) `other$password` != null else `this$password` != `other$password`) false else true
    }

    protected open fun canEqual(other: Any?): Boolean {
        return other is AuthenticationRequest
    }

    override fun hashCode(): Int {
        val PRIME = 59
        var result = 1
        val `$grantType`: Any? = grantType
        result = result * PRIME + (`$grantType`?.hashCode() ?: 43)
        val `$accessToken`: Any? = accessToken
        result = result * PRIME + (`$accessToken`?.hashCode() ?: 43)
        val `$authProvider`: Any? = authProvider
        result = result * PRIME + (`$authProvider`?.hashCode() ?: 43)
        val `$email`: Any? = email
        result = result * PRIME + (`$email`?.hashCode() ?: 43)
        val `$password`: Any? = password
        result = result * PRIME + (`$password`?.hashCode() ?: 43)
        return result
    }

    override fun toString(): String {
        return "AuthenticationRequest(grantType=" + grantType + ", accessToken=" + accessToken + ", authProvider=" + authProvider + ", email=" + email + ", password=" + password + ")"
    }
}