package com.wafflestudio.draft.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilter(authenticationManager: AuthenticationManager?, private val jwtTokenProvider: JwtTokenProvider) : BasicAuthenticationFilter(authenticationManager) {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = request.getHeader(jwtTokenProvider.headerString)
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            filterChain.doFilter(request, response)
            return
        }
        val authentication = jwtTokenProvider.getOAuth2TokenFromJwt(token)
        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }

}
