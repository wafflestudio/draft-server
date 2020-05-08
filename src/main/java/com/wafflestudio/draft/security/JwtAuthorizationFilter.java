package com.wafflestudio.draft.security;

import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;
    private JwtProperties jwtProperties;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, JwtProperties jwtProperties) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(jwtProperties.getHeaderString());
        if (header == null || !header.startsWith(jwtProperties.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(jwtProperties.getHeaderString())
                .replace(jwtProperties.getTokenPrefix(), "")
                .trim();

        String username = Jwts.parser()
                .setSigningKey(jwtProperties.getJwtSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        if (username != null) {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Cannot found user with email :" + username));
            AuthUser authUser = new AuthUser(user);
            return new UsernamePasswordAuthenticationToken(username, null, authUser.getAuthorities());
        }

        return null;
    }
}
