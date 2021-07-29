package com.wafflestudio.draft.dto

import org.springframework.lang.Nullable

class AuthenticationDTO {
    open class Request(
        open var grantType: String? = null,

        @get:Nullable
        open var accessToken: String? = null,

        @get:Nullable
        open var authProvider: String? = null,

        @get:Nullable
        open var email: String? = null,

        @get:Nullable
        open var password: String? = null
    )
}