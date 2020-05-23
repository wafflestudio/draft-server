package com.wafflestudio.draft.security.oauth2.client;

import org.springframework.security.core.AuthenticationException;

public interface OAuth2Client {
    OAuth2Response userInfo(String accessToken) throws AuthenticationException;
}
