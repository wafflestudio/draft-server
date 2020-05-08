package com.wafflestudio.draft.security;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wafflestudio.draft.security.oauth2.OAuth2ViewModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private JwtProperties jwtProperties;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProperties properties) {
        this.authenticationManager = authenticationManager;
        this.jwtProperties = properties;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/user/auth", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        OAuth2ViewModel body = null;

        try {
            body = new ObjectMapper().readValue(request.getInputStream(), OAuth2ViewModel.class);
            if (body == null) throw new IOException();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Pass user Id (not necessary), accessToken, authProvider to authenticationProvider
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                null,
                null,
                new ArrayList<>());

        authenticationToken.setDetails(body);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        AuthUser authUser = (AuthUser) authResult.getPrincipal();

        Date now = new Date();

        String token = Jwts.builder()
                .setSubject(authUser.getUsername())
                .setExpiration(new Date(now.getTime() + jwtProperties.getJwtExpirationInMs()))
                .signWith(SignatureAlgorithm.ES256, jwtProperties.getJwtSecretKey())
                .compact();
        response.addHeader(jwtProperties.getHeaderString(), jwtProperties.getTokenPrefix() + token);
    }
}
