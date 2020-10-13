package com.wafflestudio.draft.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.wafflestudio.draft.dto.request.AuthenticationRequest
import com.wafflestudio.draft.security.oauth2.OAuth2Token
import com.wafflestudio.draft.security.oauth2.client.exception.SucceedOAuthUserNotFoundException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.BufferedReader
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GeneralAuthenticationFilter(authenticationManager: AuthenticationManager?, jwtTokenProvider: JwtTokenProvider) : UsernamePasswordAuthenticationFilter() {
    private val jwtTokenProvider: JwtTokenProvider
    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
        response.addHeader("Authentication", jwtTokenProvider.generateToken(authResult))
        response.status = HttpServletResponse.SC_NO_CONTENT
    }

    @Throws(IOException::class, ServletException::class)
    override fun unsuccessfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, failed: AuthenticationException) {
//        super.unsuccessfulAuthentication(request, response, failed);
        if (failed is SucceedOAuthUserNotFoundException) {
            response.status = HttpServletResponse.SC_NOT_FOUND
        } else {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
        }
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        // Parse auth request
        val parsedRequest: AuthenticationRequest = try {
            parseRequest(request)
        } catch (e: IOException) {
            throw RuntimeException("Bad request")
        }
        val grantType = parsedRequest.grantType
        val authRequest: Authentication
        authRequest = when (grantType) {
            "OAUTH" -> OAuth2Token(null, parsedRequest)
            "PASSWORD" -> UsernamePasswordAuthenticationToken(parsedRequest.email, parsedRequest.password)
            else -> throw UsernameNotFoundException(String.format("Grant Type '%s' is not supported", grantType))
        }
        return authenticationManager.authenticate(authRequest)
    }

    @Throws(IOException::class)
    private fun parseRequest(request: HttpServletRequest): AuthenticationRequest {
        val reader: BufferedReader
        reader = request.reader
        val mapper = ObjectMapper()
        return mapper.readValue(reader, AuthenticationRequest::class.java)
    }

    init {
        this.authenticationManager = authenticationManager
        setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher("/api/v1/user/signin/", "POST"))
        this.jwtTokenProvider = jwtTokenProvider
    }
}
