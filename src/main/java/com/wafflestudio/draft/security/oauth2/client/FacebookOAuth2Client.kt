package com.wafflestudio.draft.security.oauth2.client

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Component
class FacebookOAuth2Client : OAuth2Client {
    @Throws(AuthenticationException::class)
    override fun userInfo(accessToken: String?): OAuth2Response {
        val template = RestTemplate()
        val headers: MultiValueMap<String, String> = HttpHeaders()
        val builder = UriComponentsBuilder.fromHttpUrl("$FACEBOOK_HOST/me")
                .queryParam("access_token", accessToken)
        val entity = HttpEntity<MultiValueMap<String, String>>(headers)
        val responseType: ParameterizedTypeReference<HashMap<String, Any>> = object : ParameterizedTypeReference<HashMap<String?, Any?>?>() {}
        val response: ResponseEntity<HashMap<String, Any?>> = template.exchange<HashMap<String, Any>>(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                responseType)
        if (response.statusCode != HttpStatus.OK ||
                !response.hasBody() ||
                !response.body!!.containsKey("name") ||
                response.body!!["name"] !is String) {
            throw UsernameNotFoundException("Cannot retrieve user info from FACEBOOK Auth server")
        }
        val account_name = response.body!!["name"].toString()
        println(account_name)
        //FIXME: Change account_name to email
        return OAuth2Response(OAUTH_TOKEN_PREFIX, account_name, response.statusCode)
    }

    companion object {
        const val OAUTH_TOKEN_PREFIX = "FACEBOOK"

        //    Request Example
        //
        //    GET /me/
        //    Host: https://graph.facebook.com/v7.0/
        //    Query: access_token={access_token}
        private const val FACEBOOK_HOST = "https://graph.facebook.com/v7.0/"
    }
}