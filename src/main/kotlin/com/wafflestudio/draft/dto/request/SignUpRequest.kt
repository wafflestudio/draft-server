package com.wafflestudio.draft.dto.request

data class SignUpRequest(
        val username: String,
        override var grantType: String? = null,
        override var authProvider: String? = null,
        override var accessToken: String? = null,
        override var email: String? = null,
        override var password: String? = null // TODO: More information about region, preference... should be added
) : AuthenticationRequest() {
}