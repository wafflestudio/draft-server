package com.wafflestudio.draft.dto.request

import org.springframework.lang.Nullable

open class AuthenticationRequest(
        open var grantType: String? = null,

        @get:Nullable
        open var accessToken: String? = null,

        @get:Nullable
        open var authProvider: String? = null,

        ///
        @get:Nullable
        open var email: String? = null,

        @get:Nullable
        open var password: String? = null
)
