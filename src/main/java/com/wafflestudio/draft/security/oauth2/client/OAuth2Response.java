package com.wafflestudio.draft.security.oauth2.client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OAuth2Response {
    private String authServer;
    private String email;
    private String profileUrl;
}
