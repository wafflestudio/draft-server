package com.wafflestudio.draft.security.oauth2;

import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.security.oauth2.client.KakaoOAuth2Client;
import com.wafflestudio.draft.security.oauth2.client.OAuth2Client;
import com.wafflestudio.draft.security.oauth2.client.OAuth2Response;
import com.wafflestudio.draft.security.oauth2.client.TestOAuth2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        AuthenticationRequest credentials = (AuthenticationRequest) authentication.getCredentials();
        User currentUser;

        // Access by access token
        // If success, save user in Database
        System.out.println(credentials.getAuthProvider().toUpperCase());
        System.out.println(TestOAuth2Client.OAUTH_TOKEN_PREFIX);
        switch (credentials.getAuthProvider().toUpperCase()) {
            case KakaoOAuth2Client.OAUTH_TOKEN_PREFIX:
                currentUser = loadAndUpdate(kakaoOAuth2Client, credentials);
                break;
            case TestOAuth2Client.OAUTH_TOKEN_PREFIX:
                currentUser = loadAndUpdate(testOAuth2Client, credentials);
                break;
            default:
                throw new UsernameNotFoundException("Unknown OAuth2 provider : " + credentials.getAuthProvider());
        }
        return new UsernamePasswordAuthenticationToken(currentUser, credentials, currentUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    // Fetch User data from oauth2 server and update database
    private User loadAndUpdate(OAuth2Client oAuth2Client, AuthenticationRequest credentials) {
        // Fetch User data from oauth2 server
        OAuth2Response response = oAuth2Client.userInfo(credentials.getAccessToken());

        User user = authUserService.loadUserByEmail(response.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(credentials.getAuthProvider() + "'s authentication success, but user not found in database"));

        // TODO: Update user info by response

        return authUserService.saveUser(user);
    }
}
