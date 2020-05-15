package com.wafflestudio.draft.config;

import com.wafflestudio.draft.repository.UserRepository;
import com.wafflestudio.draft.security.*;
import com.wafflestudio.draft.security.oauth2.OAuth2Provider;
import com.wafflestudio.draft.security.password.UserPrincipalDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.wafflestudio.draft.security.JwtAuthenticationEntryPoint;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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

    private final UserPrincipalDetailService userPrincipalDetailService;

    private static final String[] AUTH_WHITELIST_SWAGGER = {
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    public SecurityConfig(UserRepository userRepository, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtTokenProvider jwtTokenProvider, UserPrincipalDetailService userPrincipalDetailService) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userPrincipalDetailService = userPrincipalDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(oAuth2AuthenticationProvider())
                .authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    OAuth2Provider oAuth2AuthenticationProvider() {
        return new OAuth2Provider();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(this.userPrincipalDetailService);

        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilter(new GeneralAuthenticationFilter(authenticationManager(), jwtTokenProvider))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtTokenProvider))
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST_SWAGGER).permitAll()     // Swagger document
                .antMatchers("/auth").permitAll()   // Auth entrypoint
                .antMatchers("/api/v1/user/signup").permitAll() // SignUp user
                .antMatchers("/unsecured").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(AUTH_WHITELIST_SWAGGER);
    }
}
