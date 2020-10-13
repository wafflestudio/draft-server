package com.wafflestudio.draft.security.oauth2.client

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.util.*

@Component
class KakaoOAuth2Client : OAuth2Client {
    @Throws(AuthenticationException::class)
    override fun userInfo(accessToken: String?): OAuth2Response {
        val template = RestTemplate()
        val headers: MultiValueMap<String, String> = HttpHeaders()
        headers.add("Authorization", "$TOKEN_PREFIX $accessToken")
        headers.add("property_keys", "[\"kakao_account.email\"]")
        val entity = HttpEntity<MultiValueMap<String, String>>(headers)
        val responseType: ParameterizedTypeReference<HashMap<String, Any>> = object : ParameterizedTypeReference<HashMap<String?, Any?>?>() {}
        val response: ResponseEntity<HashMap<String, Any?>> = template.exchange<HashMap<String, Any>>("$KAKAO_HOST/v2/user/me", HttpMethod.GET, entity, responseType)
        if (response.statusCode != HttpStatus.OK ||
                !response.hasBody() ||
                !response.body!!.containsKey("kakao_account") ||
                response.body!!["kakao_account"] !is HashMap<*, *>) {
            throw UsernameNotFoundException("Cannot retrieve user info from KAKAO Auth server")
        }
        val account = response.body!!["kakao_account"] as HashMap<String, Any>?
        if (!account!!["is_email_valid"] as Boolean) {
            throw UsernameNotFoundException("Email information not exist or not agreed")
        }
        println(account["email"].toString())
        println(response.toString())


        // TODO: Make up kakao response
        return OAuth2Response(OAUTH_TOKEN_PREFIX, account["email"].toString(), response.statusCode)
    }

    companion object {
        const val OAUTH_TOKEN_PREFIX = "KAKAO"

        //    Request Example
        //
        //    GET/POST /v2/user/me HTTP/1.1
        //    Host: kapi.kakao.com
        //    Authorization: Bearer {access_token}
        //    Content-type: application/x-www-form-urlencoded;charset=utf-8
        private const val KAKAO_HOST = "https://kapi.kakao.com"
        private const val TOKEN_PREFIX = "Bearer"
    }
}