package com.wafflestudio.draft.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.jwt-secret-key}")
    private String jwtSecretKey;

    @Value("${app.jwt.jwt-expiration-in-ms")
    private int jwtExpirationInMs;

    public String generateToken;



}
