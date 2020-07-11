package com.wafflestudio.draft.dto.request;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class AuthenticationRequest {
    private String grantType;

    @Nullable
    private String accessToken;
    @Nullable
    private String authProvider;
///
    @Nullable
    private String email;
    @Nullable
    private String password;
}
