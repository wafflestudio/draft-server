package com.wafflestudio.draft.config;

import com.wafflestudio.draft.repository.UserRepository;
import com.wafflestudio.draft.security.*;
import com.wafflestudio.draft.security.oauth2.OAuth2Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import com.wafflestudio.draft.security.JwtAuthenticationEntryPoint;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtTokenProvider jwtTokenProvider;

    private static final String[] AUTH_WHITELIST_SWAGGER = {
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    public SecurityConfig(UserRepository userRepository, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtTokenProvider jwtTokenProvider) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    OAuth2Provider authenticationProvider() {
        return new OAuth2Provider();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtTokenProvider))
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST_SWAGGER).permitAll()     // Swagger document
                .antMatchers("/api/v1/user/auth").permitAll()   // Auth entrypoint
                .antMatchers("/unsecured").permitAll()
                .antMatchers("/api/v1/test").hasRole("TEST_AUTH")
                .anyRequest().authenticated();

    }
}
