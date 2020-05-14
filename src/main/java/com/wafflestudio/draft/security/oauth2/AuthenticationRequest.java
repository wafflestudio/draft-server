package com.wafflestudio.draft.security.oauth2;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class AuthenticationRequest {
    private String accessToken;
    private String authProvider;

    @Nullable
    private int id;
}