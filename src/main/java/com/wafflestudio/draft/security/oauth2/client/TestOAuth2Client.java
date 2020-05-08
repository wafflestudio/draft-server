package com.wafflestudio.draft.security.oauth2.client;

import org.springframework.stereotype.Component;

@Component
public class TestOAuth2Client implements OAuth2Client {

    static final public String OAUTH_TOKEN_PREFIX = "TEST";

    @Override
    public OAuth2Response userInfo(String accessToken) {
        return new OAuth2Response("TEST", "testuser@test.com", "testprofile/urls");
    }
}
