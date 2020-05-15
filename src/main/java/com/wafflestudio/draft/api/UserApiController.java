package com.wafflestudio.draft.api;


import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.model.request.SignUpRequest;
import com.wafflestudio.draft.security.CurrentUser;
import com.wafflestudio.draft.security.oauth2.AuthUserService;
import com.wafflestudio.draft.security.oauth2.OAuth2Provider;
import com.wafflestudio.draft.security.oauth2.client.OAuth2Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
public class UserApiController {

    private final OAuth2Provider oAuth2Provider;
    private final AuthUserService authUserService;
    private final PasswordEncoder passwordEncoder;

    public UserApiController(OAuth2Provider oAuth2Provider, AuthUserService authUserService, PasswordEncoder passwordEncoder) {
        this.oAuth2Provider = oAuth2Provider;
        this.authUserService = authUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity createUser(@RequestBody SignUpRequest signUpRequest, HttpServletResponse response) throws IOException {
        User user;

        switch (signUpRequest.getGrantType()) {
            case "OAUTH":
                OAuth2Response oAuth2Response = oAuth2Provider.requestAuthentication(signUpRequest);
                if (oAuth2Response.getStatus() != HttpStatus.OK) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Token for auth server is not valid");
                    return null;
                }

                user = new User(signUpRequest.getUserName(), oAuth2Response.getEmail());
                break;
            case "PASSWORD":
                if (signUpRequest.getUserName() == null || signUpRequest.getEmail() == null || signUpRequest.getPassword() == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return null;
                }

                user = new User(signUpRequest.getUserName(), signUpRequest.getEmail());
                user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Grant type not valid");
                return null;
        }

        authUserService.saveUser(user);

        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public String myInfo(@CurrentUser User currentUser) {
        System.out.println(currentUser);
        return currentUser.getEmail();
    }
}
