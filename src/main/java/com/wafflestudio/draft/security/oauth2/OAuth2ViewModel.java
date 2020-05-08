package com.wafflestudio.draft.security.oauth2;

import lombok.Data;

@Data
public class OAuth2ViewModel {
    private String accessToken;
    private String authProvider;
    private int id;
}