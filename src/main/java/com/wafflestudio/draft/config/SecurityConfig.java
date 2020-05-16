package com.wafflestudio.draft.config;

import com.wafflestudio.draft.security.GeneralAuthenticationFilter;
import com.wafflestudio.draft.security.JwtAuthenticationEntryPoint;
import com.wafflestudio.draft.security.JwtAuthorizationFilter;
import com.wafflestudio.draft.security.JwtTokenProvider;
import com.wafflestudio.draft.security.oauth2.OAuth2Provider;
import com.wafflestudio.draft.security.oauth2.client.KakaoOAuth2Client;
import com.wafflestudio.draft.security.oauth2.client.TestOAuth2Client;
import com.wafflestudio.draft.security.password.UserPrincipalDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserPrincipalDetailService userPrincipalDetailService;

    private final KakaoOAuth2Client kakaoOAuth2Client;

    private final TestOAuth2Client testOAuth2Client;

    private static final String[] AUTH_WHITELIST_SWAGGER = {
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtTokenProvider jwtTokenProvider, UserPrincipalDetailService userPrincipalDetailService, KakaoOAuth2Client kakaoOAuth2Client, TestOAuth2Client testOAuth2Client) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userPrincipalDetailService = userPrincipalDetailService;
        this.kakaoOAuth2Client = kakaoOAuth2Client;
        this.testOAuth2Client = testOAuth2Client;
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
        OAuth2Provider provider = new OAuth2Provider();
        provider.addOAuth2Client(KakaoOAuth2Client.OAUTH_TOKEN_PREFIX, kakaoOAuth2Client);
        provider.addOAuth2Client(TestOAuth2Client.OAUTH_TOKEN_PREFIX, testOAuth2Client);
        return provider;
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
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(AUTH_WHITELIST_SWAGGER);
    }
}
