package com.wafflestudio.draft.security.oauth2.client.exception;

import org.springframework.security.core.AuthenticationException;

public class SucceedOAuthUserNotFoundException extends AuthenticationException {
    public SucceedOAuthUserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public SucceedOAuthUserNotFoundException(String msg) {
        super(msg);
    }
}
