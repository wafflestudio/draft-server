package com.wafflestudio.draft.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wafflestudio.draft.dto.request.AuthenticationRequest;
import com.wafflestudio.draft.security.oauth2.OAuth2Token;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class GeneralAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtTokenProvider jwtTokenProvider;

    public GeneralAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        super();
        this.setAuthenticationManager(authenticationManager);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/v1/user/signin/", "POST"));
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        response.addHeader("Authentication", jwtTokenProvider.generateToken(authResult));
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
//        super.unsuccessfulAuthentication(request, response, failed);
        //FIXME: Please fix it to compare with Exception type(not message)
        if (failed.getMessage().contains("Authentication success")) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage());
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // Parse auth request
        AuthenticationRequest parsedRequest;
        try {
            parsedRequest = parseRequest(request);
        } catch (IOException e) {
            throw new RuntimeException("Bad request");
        }

        String grantType = parsedRequest.getGrantType();

        Authentication authRequest;

        switch (grantType) {
            case "OAUTH":
                authRequest = new OAuth2Token(null, parsedRequest);
                break;
            case "PASSWORD":
                authRequest = new UsernamePasswordAuthenticationToken(parsedRequest.getEmail(), parsedRequest.getPassword());
                break;
            default:
                throw new UsernameNotFoundException(String.format("Grant Type '%s' is not supported", grantType));
        }

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private AuthenticationRequest parseRequest(HttpServletRequest request) throws IOException {
        BufferedReader reader;

        reader = request.getReader();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(reader, AuthenticationRequest.class);

    }
}
