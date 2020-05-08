package com.wafflestudio.draft.security;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix="app.jwt")
public class JwtProperties {
    private String jwtSecretKey;
    private int jwtExpirationInMs;

    // Default : Bearer + white space
    private final String tokenPrefix = "Bearer ";
    private final String headerString = "Authentication";
}
