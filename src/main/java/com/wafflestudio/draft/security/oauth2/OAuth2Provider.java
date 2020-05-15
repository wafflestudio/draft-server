package com.wafflestudio.draft.security.oauth2;

import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.model.request.AuthenticationRequest;
import com.wafflestudio.draft.security.oauth2.client.KakaoOAuth2Client;
import com.wafflestudio.draft.security.oauth2.client.OAuth2Client;
import com.wafflestudio.draft.security.oauth2.client.OAuth2Response;
import com.wafflestudio.draft.security.oauth2.client.TestOAuth2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class OAuth2Provider implements AuthenticationProvider {
    @Autowired
    private KakaoOAuth2Client kakaoOAuth2Client;

    @Autowired
    private TestOAuth2Client testOAuth2Client;

    @Autowired
    private AuthUserService authUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticationRequest request = (AuthenticationRequest) authentication.getCredentials();
        OAuth2Response response;
        User currentUser;

        System.out.println("YAY" + request.toString());

        // Request authenticate to auth server by access token
        response = requestAuthentication(request);
        if (response.getStatus() != HttpStatus.OK)
            throw new UsernameNotFoundException("User token failed to authenticate on " + request.getAuthProvider());

        // If success, save user in Database
        currentUser = loadAndUpdate(response);

        return new OAuth2Token(currentUser, null, currentUser.getAuthorities());
    }

    public OAuth2Response requestAuthentication(AuthenticationRequest request) {
        OAuth2Client authServer;

        switch (request.getAuthProvider().toUpperCase()) {
            case KakaoOAuth2Client.OAUTH_TOKEN_PREFIX:
                authServer = kakaoOAuth2Client;
                break;
            case TestOAuth2Client.OAUTH_TOKEN_PREFIX:
                authServer = testOAuth2Client;
                break;
            default:
                throw new UsernameNotFoundException(String.format("Unknown OAuth2 provider '%s'", request.getAuthProvider()));
        }
        return authServer.userInfo(request.getAccessToken());
    }

    // Fetch User data from oauth2 server and update database
    private User loadAndUpdate(OAuth2Response response) {
        User user = authUserService.loadUserByEmail(response.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Authentication success, but user email '%s' is not found in database", response.getEmail())));

        // TODO: Update user info by response
        return authUserService.saveUser(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(OAuth2Token.class);
    }
}
