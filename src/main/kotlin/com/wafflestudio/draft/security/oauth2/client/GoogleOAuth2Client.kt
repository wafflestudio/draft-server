package com.wafflestudio.draft.security.oauth2.client

import com.wafflestudio.draft.security.oauth2.client.exception.OAuthTokenNotValidException
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import org.springframework.http.*
import org.springframework.http.client.ClientHttpResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException

@Component
class GoogleOAuth2Client : OAuth2Client {
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
        val builder: UriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(GOOGLE_HOST.toString() + "/tokeninfo")
                .queryParam("id_token", accessToken)
        val headers = HttpHeaders()
        val httpEntity: HttpEntity<String?> = HttpEntity(headers)
        val response: ResponseEntity<String?> = template.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, String::class.java)
        if (response.statusCode !== HttpStatus.OK ||
                !response.hasBody()) {
            throw OAuthTokenNotValidException("Cannot retrieve user info from GOOGLE Auth server")
        }
        val body: String = response.body!!
        try {
            val jsonBody: JSONObject = JSONParser().parse(body) as JSONObject
            val email = jsonBody["email"] as String
            return OAuth2Response(OAUTH_TOKEN_PREFIX, email, response.statusCode)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        throw OAuthTokenNotValidException("Cannot retrieve user info from GOOGLE Auth server, user info is not parcelable")
    }

    companion object {
        const val OAUTH_TOKEN_PREFIX: String = "GOOGLE"

        // Request Example
        //
        // GET /tokeninfo
        // Host: https://oauth2.googleapis.com
        // Query: id_token={idToken}
        // reference : https://developers.google.com/identity/sign-in/web/backend-auth
        private val GOOGLE_HOST: String? = "https://oauth2.googleapis.com/"
    }
}