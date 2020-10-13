package com.wafflestudio.draft.config

import com.wafflestudio.draft.security.GeneralAuthenticationFilter
import com.wafflestudio.draft.security.JwtAuthenticationEntryPoint
import com.wafflestudio.draft.security.JwtAuthorizationFilter
import com.wafflestudio.draft.security.JwtTokenProvider
import com.wafflestudio.draft.security.oauth2.OAuth2Provider
import com.wafflestudio.draft.security.oauth2.client.FacebookOAuth2Client
import com.wafflestudio.draft.security.oauth2.client.GoogleOAuth2Client
import com.wafflestudio.draft.security.oauth2.client.KakaoOAuth2Client
import com.wafflestudio.draft.security.oauth2.client.TestOAuth2Client
import com.wafflestudio.draft.security.password.UserPrincipalDetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
class SecurityConfig(private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
                     private val jwtTokenProvider: JwtTokenProvider,
                     private val userPrincipalDetailService: UserPrincipalDetailService,
                     private val kakaoOAuth2Client: KakaoOAuth2Client,
                     private val testOAuth2Client: TestOAuth2Client,
                     private val googleOAuth2Client: GoogleOAuth2Client,
                     private val facebookOAuth2Client: FacebookOAuth2Client
) : WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
                .authenticationProvider(oAuth2AuthenticationProvider())
                .authenticationProvider(daoAuthenticationProvider())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun oAuth2AuthenticationProvider(): OAuth2Provider {
        val provider = OAuth2Provider()
        provider.addOAuth2Client(KakaoOAuth2Client.Companion.OAUTH_TOKEN_PREFIX, kakaoOAuth2Client)
        provider.addOAuth2Client(FacebookOAuth2Client.Companion.OAUTH_TOKEN_PREFIX, facebookOAuth2Client)
        provider.addOAuth2Client(TestOAuth2Client.Companion.OAUTH_TOKEN_PREFIX, testOAuth2Client)
        provider.addOAuth2Client(GoogleOAuth2Client.OAUTH_TOKEN_PREFIX, googleOAuth2Client);
        return provider
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder())
        provider.setUserDetailsService(userPrincipalDetailService)
        return provider
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilter(GeneralAuthenticationFilter(authenticationManager(), jwtTokenProvider))
                .addFilter(JwtAuthorizationFilter(authenticationManager(), jwtTokenProvider))
                .authorizeRequests()
                .antMatchers(*AUTH_WHITELIST_SWAGGER).permitAll() // Swagger document
                .antMatchers("/api/v1/user/signin/").permitAll()  // Auth entrypoint
                .antMatchers("/api/v1/user/signup/").permitAll()  // SignUp user
                .antMatchers("/api/v1/user/check-username/").permitAll()
                .anyRequest().authenticated()
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(*AUTH_WHITELIST_SWAGGER)
    }

    companion object {
        private val AUTH_WHITELIST_SWAGGER = arrayOf( // -- swagger ui
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/webjars/**"
        )
    }

}