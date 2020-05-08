package com.wafflestudio.draft.security.oauth2;

import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.UserRepository;
import com.wafflestudio.draft.security.AuthUser;
import com.wafflestudio.draft.security.oauth2.client.KakaoOAuth2Client;
import com.wafflestudio.draft.security.oauth2.client.OAuth2Client;
import com.wafflestudio.draft.security.oauth2.client.OAuth2Response;
import com.wafflestudio.draft.security.oauth2.client.TestOAuth2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class OAuth2Provider implements AuthenticationProvider {
    @Autowired
    private KakaoOAuth2Client kakaoOAuth2Client;

    @Autowired
    private TestOAuth2Client testOAuth2Client;

    @Autowired
    private AuthUserService authUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2ViewModel details = (OAuth2ViewModel) authentication.getDetails();
        User currentUser;

        // Access by access token
        // If success, save user in Database

        switch (details.getAuthProvider().toUpperCase()) {
            case KakaoOAuth2Client.OAUTH_TOKEN_PREFIX:
                currentUser = loadAndUpdate(kakaoOAuth2Client, details);
                break;
            case TestOAuth2Client.OAUTH_TOKEN_PREFIX:
                currentUser = loadAndUpdate(testOAuth2Client, details);
                break;
            default:
                throw new UsernameNotFoundException("Unknown OAuth2 provider : " + details.getAuthProvider());
        }
        return new UsernamePasswordAuthenticationToken(new AuthUser(currentUser), null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    // Fetch User data from oauth2 server and update database
    private User loadAndUpdate(OAuth2Client oAuth2Client, OAuth2ViewModel credentials) {
        // Fetch User data from oauth2 server
        OAuth2Response response = oAuth2Client.userInfo(credentials.getAccessToken());

        User user = authUserService.loadUserByEmail(response.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(credentials.getAuthProvider() + "'s authentication success, but user not found in database"));


        // TODO: Update user info by response

        return authUserService.saveUser(user);
    }
}
