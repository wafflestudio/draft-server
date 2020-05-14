package com.wafflestudio.draft.api;


import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.security.CurrentUser;
import com.wafflestudio.draft.security.JwtTokenProvider;
import com.wafflestudio.draft.security.oauth2.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
public class UserApiController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    public UserApiController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/auth")
    public void authenticate(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(null, authenticationRequest);

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(token);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        response.addHeader("Authentication", jwtTokenProvider.generateToken(authentication));
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @GetMapping("/me")
    public String myInfo(@CurrentUser User currentUser) {
        System.out.println(currentUser);
        return currentUser.getEmail();
    }
}
