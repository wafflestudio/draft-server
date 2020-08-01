package com.wafflestudio.draft.security.oauth2.client.exception;

import org.springframework.security.core.AuthenticationException;

public class OAuthTokenNotValidException extends AuthenticationException {
    public OAuthTokenNotValidException(String msg, Throwable t) {
        super(msg, t);
    }

    public OAuthTokenNotValidException(String msg) {
        super(msg);
    }

}
