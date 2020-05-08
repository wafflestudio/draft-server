package com.wafflestudio.draft.config;

import com.wafflestudio.draft.repository.UserRepository;
import com.wafflestudio.draft.security.*;
import com.wafflestudio.draft.security.oauth2.OAuth2Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

    private final UserRepository userRepository;

    private final JwtProperties jwtProperties;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(UserRepository userRepository, JwtProperties properties, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.userRepository = userRepository;
        this.jwtProperties = properties;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
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
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtProperties))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository, jwtProperties))
                .authorizeRequests()
                .antMatchers("/user/auth").permitAll()
                .antMatchers("/unsecured").permitAll()
                .anyRequest().authenticated();

    }
}
