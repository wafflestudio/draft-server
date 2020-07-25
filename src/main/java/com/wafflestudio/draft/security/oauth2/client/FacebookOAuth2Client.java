package com.wafflestudio.draft.security.oauth2.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;

@Component
public class FacebookOAuth2Client implements OAuth2Client {
    public static final String OAUTH_TOKEN_PREFIX = "FACEBOOK";

//    Request Example
//
//    GET /me/
//    Host: https://graph.facebook.com/v7.0/
//    Query: access_token={access_token}

    private static final String FACEBOOK_HOST = "https://graph.facebook.com/v7.0/";

    @Override
    public OAuth2Response userInfo(String accessToken) throws AuthenticationException {

        RestTemplate template = new RestTemplate();

        MultiValueMap<String, String> headers = new HttpHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(FACEBOOK_HOST + "/me")
                .queryParam("access_token", accessToken);


        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
        ParameterizedTypeReference<HashMap<String, Object>> responseType =
                new ParameterizedTypeReference<HashMap<String, Object>>() {
                };

        ResponseEntity<HashMap<String, Object>> response = template.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                responseType);

        if (response.getStatusCode() != HttpStatus.OK ||
                !response.hasBody() ||
                !response.getBody().containsKey("name") ||
                !(response.getBody().get("name") instanceof String)) {
            throw new UsernameNotFoundException("Cannot retrieve user info from FACEBOOK Auth server");
        }

        String account_name = response.getBody().get("name").toString();

        System.out.println(account_name);
        //FIXME: Change account_name to email

        return new OAuth2Response(OAUTH_TOKEN_PREFIX, account_name, response.getStatusCode());
    }
}
