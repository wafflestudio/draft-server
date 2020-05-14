package com.wafflestudio.draft.security;

import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.security.oauth2.AuthUserService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

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

        Map<String, Object> claims = new HashMap<>();
        List<String> li = new ArrayList<>();

        for (GrantedAuthority item : user.getAuthorities()) {
            li.add(item.getAuthority());
        }

        claims.put("email", user.getEmail());
        claims.put("role", li);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return tokenPrefix + Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public Authentication getUsernameTokenFromJwt(String token) {
        token = removePrefix(token);

        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();

        // Recover user role from JWT
        List<String> ls = claims.get("role", List.class);
        Collection<GrantedAuthority> authorises = new ArrayList<>();
        for (String item : ls) {
            authorises.add(new SimpleGrantedAuthority(item));
        }

        // Recover User class from JWT
        String email = claims.get("email", String.class);
        User currentUser = authUserService.loadUserByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException(email + " is not valid email, check token is expired"));

        // Make token with parsed data
        return new UsernamePasswordAuthenticationToken(currentUser, null, authorises);
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
