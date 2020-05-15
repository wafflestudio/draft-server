package com.wafflestudio.draft.security.oauth2;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class OAuth2Token extends AbstractAuthenticationToken {
    private final Object principal;
    private Object accessToken;

    public OAuth2Token(Object principal, Object accessToken) {
        super(null);
        this.principal = principal;
        this.accessToken = accessToken;
        super.setAuthenticated(false);
    }

    public OAuth2Token(Object principal, Object accessToken, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.accessToken = accessToken;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return accessToken;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        accessToken = null;
    }
}
