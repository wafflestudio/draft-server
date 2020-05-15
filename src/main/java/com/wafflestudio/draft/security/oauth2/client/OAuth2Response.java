package com.wafflestudio.draft.security.oauth2.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class OAuth2Response {
    private String authServer;
    private String email;
    private HttpStatus status;
}
