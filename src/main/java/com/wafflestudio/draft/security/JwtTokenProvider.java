package com.wafflestudio.draft.security;

import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.security.oauth2.AuthUserService;
import com.wafflestudio.draft.security.oauth2.OAuth2Token;
import com.wafflestudio.draft.security.password.UserPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    public final String tokenPrefix = "Bearer ";

    public final String headerString = "Authentication";

    @Value("${app.jwt.jwt-secret-key}")
    private String jwtSecretKey;

    @Value("${app.jwt.jwt-expiration-in-ms}")
    private Long jwtExpirationInMs;

    private final AuthUserService authUserService;

    public JwtTokenProvider(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }


    // Generate jwt token with prefix
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return generateToken(user.getEmail());
    }

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return tokenPrefix + Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public Authentication getOAuth2TokenFromJwt(String token) {
        token = removePrefix(token);

        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();


        // Recover User class from JWT
        String email = claims.get("email", String.class);
        User currentUser = authUserService.loadUserByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException(email + " is not valid email, check token is expired"));

        UserPrincipal userPrincipal = new UserPrincipal(currentUser);
        Collection<? extends GrantedAuthority> authorises = userPrincipal.getAuthorities();
        System.out.println(authorises);
        // Make token with parsed data
        return new OAuth2Token(userPrincipal, null, authorises);
    }

    public boolean validateToken(String authToken) {
        if (authToken == null) {
            return false;
        }

        if (!authToken.startsWith(tokenPrefix)) {
            logger.error("Token not match type Bearer");
            return false;
        }

        authToken = removePrefix(authToken);

        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    public String removePrefix(String tokenWithPrefix) {
        return tokenWithPrefix.replace(tokenPrefix, "").trim();
    }
}
