package com.wafflestudio.draft.security.oauth2

import com.wafflestudio.draft.dto.request.AuthenticationRequest
import com.wafflestudio.draft.model.User
import com.wafflestudio.draft.security.oauth2.OAuth2Token
import com.wafflestudio.draft.security.oauth2.client.OAuth2Client
import com.wafflestudio.draft.security.oauth2.client.OAuth2Response
import com.wafflestudio.draft.security.oauth2.client.exception.OAuthTokenNotValidException
import com.wafflestudio.draft.security.oauth2.client.exception.SucceedOAuthUserNotFoundException
import com.wafflestudio.draft.security.password.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.util.*

class OAuth2Provider : AuthenticationProvider {
    @Autowired
    val authUserService: AuthUserService? = null
    val oAuth2ClientMap: MutableMap<String, OAuth2Client> = HashMap()

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val request = authentication.credentials as AuthenticationRequest
        val response: OAuth2Response?
        val currentUser: User?
        response = requestAuthentication(request)
        if (response.status != HttpStatus.OK) throw OAuthTokenNotValidException("User token failed to authenticate on " + request.authProvider)
        currentUser = loadAndUpdate(response)
        val userPrincipal = UserPrincipal(currentUser!!)
        return OAuth2Token(userPrincipal, null, userPrincipal.authorities)
    }

    // Request authenticate to auth server by access token
    @Throws(AuthenticationException::class)
    fun requestAuthentication(request: AuthenticationRequest): OAuth2Response {
        if (request.authProvider == null) throw UsernameNotFoundException("authServer is not given")
        val authServer = oAuth2ClientMap[request.authProvider!!.toUpperCase()]
                ?: throw UsernameNotFoundException(String.format("Unknown OAuth2 provider '%s'", request.authProvider))
        return authServer.userInfo(request.accessToken)
    }

    // If authentication from request succeed, check user exist in Database. If not, throw error
    // Fetch User data from oauth2 server and update database
    private fun loadAndUpdate(response: OAuth2Response): User? {
        val user = authUserService!!.loadUserByEmail(response.email)
                ?: throw SucceedOAuthUserNotFoundException(String.format("Authentication success, but user email '%s' is not found in database", response.email))

        // TODO: Update user info by response
        return authUserService.saveUser(user)
    }

    // Add source of authentication
    fun addOAuth2Client(key: String, client: OAuth2Client) {
        oAuth2ClientMap[key] = client
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == OAuth2Token::class.java
    }
}