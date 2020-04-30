package com.wafflestudio.draft.config;

import com.nimbusds.oauth2.sdk.auth.JWTAuthentication;
import com.wafflestudio.draft.security.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = false
        // Role 에 따라 서비스, 컨트롤러 접근을 제한할 수 있는 annotation을 제공합니다.
        // 우선은 전부 false로 지정했습니다.
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

//    @Bean
//    pubilc JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JWTAuthenticationFilter();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                    .and()
                .sessionManagement()
                    .and()
                .authorizeRequests()
                    .antMatchers("/",
                        "/**/*.jpg",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.css",
                        "/**/*.html").permitAll()
                .anyRequest().authenticated()
            .and()
                .oauth2Login();

        super.configure(http);
    }
}
