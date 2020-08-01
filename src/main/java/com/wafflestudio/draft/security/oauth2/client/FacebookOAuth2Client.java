package com.wafflestudio.draft.security.oauth2.client;

import com.wafflestudio.draft.security.oauth2.client.exception.OAuthTokenNotValidException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

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
        template.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        });

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(FACEBOOK_HOST + "/me")
                .queryParam("access_token", accessToken);

        MultiValueMap<String, String> headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, String.class);

        if (response.getStatusCode() != HttpStatus.OK || !response.hasBody()) {
            throw new OAuthTokenNotValidException("Cannot retrieve user info from FACEBOOK Auth server");
        }

        String body = response.getBody();
        try {
            JSONObject jsonBody = (JSONObject) new JSONParser().parse(body);
            String fakeEmail = (String) jsonBody.get("name");
            // FIXME : Need general identifier
            return new OAuth2Response(OAUTH_TOKEN_PREFIX, fakeEmail, response.getStatusCode());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        throw new OAuthTokenNotValidException("Cannot retrieve user info from FACEBOOK Auth server, user info is not parcelable");
    }
}
