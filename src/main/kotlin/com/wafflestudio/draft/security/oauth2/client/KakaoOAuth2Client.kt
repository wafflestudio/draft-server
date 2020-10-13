package com.wafflestudio.draft.security.oauth2.client

import com.wafflestudio.draft.security.oauth2.client.exception.OAuthTokenNotValidException
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import org.springframework.http.*
import org.springframework.http.client.ClientHttpResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException
import kotlin.jvm.Throws
import com.wafflestudio.draft.service.FCMService
import com.wafflestudio.draft.service.FCMInitializer
import com.wafflestudio.draft.security.oauth2.client.KakaoOAuth2Client
import com.wafflestudio.draft.security.oauth2.client.FacebookOAuth2Client
import com.wafflestudio.draft.security.JwtTokenProvider

@Component
class KakaoOAuth2Client : OAuth2Client {
    @Throws(AuthenticationException::class)
    override fun userInfo(accessToken: String?): OAuth2Response {
        val template = RestTemplate()
        template.errorHandler = object : ResponseErrorHandler {
            @Throws(IOException::class)
            override fun hasError(response: ClientHttpResponse): Boolean {
                return false
            }

            @Throws(IOException::class)
            override fun handleError(response: ClientHttpResponse) {
            }
        }
        val builder: UriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_HOST.toString() + "/v2/user/me")
        val headers: MultiValueMap<String?, String?> = HttpHeaders()
        headers.add("Authorization", TOKEN_PREFIX.toString() + " " + accessToken)
        headers.add("property_keys", "[\"kakao_account.email\"]")
        val httpEntity: HttpEntity<MultiValueMap<String?, String?>?> = HttpEntity(headers)
        val response: ResponseEntity<String?> = template.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, String::class.java)
        if (response.statusCode !== HttpStatus.OK ||
                !response.hasBody()) {
            throw OAuthTokenNotValidException("Cannot retrieve user info from KAKAO Auth server")
        }
        val body: String = response.body!!
        try {
            val jsonBody: JSONObject = (JSONParser().parse(body) as JSONObject)["kakao_account"] as JSONObject
            val email = jsonBody["email"] as String
            return OAuth2Response(OAUTH_TOKEN_PREFIX, email, response.statusCode)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        throw OAuthTokenNotValidException("Cannot retrieve user info from KAKAO Auth server, user info is not parcelable")
    }

    companion object {
        const val OAUTH_TOKEN_PREFIX: String = "KAKAO"

        //    Request Example
        //
        //    GET/POST /v2/user/me HTTP/1.1
        //    Host: kapi.kakao.com
        //    Authorization: Bearer {access_token}
        //    Content-type: application/x-www-form-urlencoded;charset=utf-8
        private val KAKAO_HOST: String? = "https://kapi.kakao.com"
        private val TOKEN_PREFIX: String? = "Bearer"
    }
}