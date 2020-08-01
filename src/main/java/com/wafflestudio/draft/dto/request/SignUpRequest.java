package com.wafflestudio.draft.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class SignUpRequest extends AuthenticationRequest {
    @NotNull
    private String username;
    @NotNull
    private String grantType;
    
    private String authProvider;
    private String accessToken;
    private String email;
    private String password;

    // TODO: More information about region, preference... should be added
}
