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

@Component
class FacebookOAuth2Client : OAuth2Client {
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
        val builder: UriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(FACEBOOK_HOST.toString() + "/me")
                .queryParam("access_token", accessToken)
        val headers: MultiValueMap<String?, String?> = HttpHeaders()
        val httpEntity: HttpEntity<MultiValueMap<String?, String?>?> = HttpEntity(headers)
        val response: ResponseEntity<String?> = template.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, String::class.java)
        if (response.statusCode !== HttpStatus.OK || !response.hasBody()) {
            throw OAuthTokenNotValidException("Cannot retrieve user info from FACEBOOK Auth server")
        }
        val body: String = response.body!!
        try {
            val jsonBody: JSONObject = JSONParser().parse(body) as JSONObject
            val fakeEmail = jsonBody["name"] as String
            // FIXME : Need general identifier
            return OAuth2Response(OAUTH_TOKEN_PREFIX, fakeEmail, response.statusCode)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        throw OAuthTokenNotValidException("Cannot retrieve user info from FACEBOOK Auth server, user info is not parcelable")
    }

    companion object {
        const val OAUTH_TOKEN_PREFIX: String = "FACEBOOK"

        //    Request Example
        //
        //    GET /me/
        //    Host: https://graph.facebook.com/v7.0/
        //    Query: access_token={access_token}
        private val FACEBOOK_HOST: String? = "https://graph.facebook.com/v7.0/"
    }
}
