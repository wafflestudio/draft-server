package com.wafflestudio.draft.security.oauth2;

import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.model.request.AuthenticationRequest;
import com.wafflestudio.draft.security.oauth2.client.OAuth2Client;
import com.wafflestudio.draft.security.oauth2.client.OAuth2Response;
import com.wafflestudio.draft.security.password.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class OAuth2Provider implements AuthenticationProvider {
    @Autowired
    private AuthUserService authUserService;

    private Map<String, OAuth2Client> oAuth2ClientMap = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticationRequest request = (AuthenticationRequest) authentication.getCredentials();
        OAuth2Response response;
        User currentUser;

        response = requestAuthentication(request);
        if (response.getStatus() != HttpStatus.OK)
            throw new UsernameNotFoundException("User token failed to authenticate on " + request.getAuthProvider());

        currentUser = loadAndUpdate(response);
        UserPrincipal userPrincipal = new UserPrincipal(currentUser);

        return new OAuth2Token(userPrincipal, null, userPrincipal.getAuthorities());
    }

    // Request authenticate to auth server by access token
    public OAuth2Response requestAuthentication(AuthenticationRequest request) {
        if (request.getAuthProvider() == null)
            throw new UsernameNotFoundException("authServer is not given");

        OAuth2Client authServer = oAuth2ClientMap.get(request.getAuthProvider().toUpperCase());

        if (authServer == null)
            throw new UsernameNotFoundException(String.format("Unknown OAuth2 provider '%s'", request.getAuthProvider()));

        return authServer.userInfo(request.getAccessToken());
    }

    // If authentication from request succeed, check user exist in Database. If not, throw error
    // Fetch User data from oauth2 server and update database
    private User loadAndUpdate(OAuth2Response response) {
        User user = authUserService.loadUserByEmail(response.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Authentication success, but user email '%s' is not found in database", response.getEmail())));

        // TODO: Update user info by response
        return authUserService.saveUser(user);
    }

    // Add source of authentication
    public void addOAuth2Client(String key, OAuth2Client client) {
        oAuth2ClientMap.put(key, client);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(OAuth2Token.class);
    }
}
