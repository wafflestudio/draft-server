package com.wafflestudio.draft.security.oauth2.client;

import com.wafflestudio.draft.security.oauth2.client.exception.OAuthTokenNotValidException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class GoogleOAuth2Client implements OAuth2Client {
    public static final String OAUTH_TOKEN_PREFIX = "GOOGLE";


// Request Example
//
// GET /tokeninfo
// Host: https://oauth2.googleapis.com
// Query: id_token={idToken}
// reference : https://developers.google.com/identity/sign-in/web/backend-auth

    private static final String GOOGLE_HOST = "https://oauth2.googleapis.com/";

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
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(GOOGLE_HOST + "/tokeninfo")
                .queryParam("id_token", accessToken);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, String.class);

        if (response.getStatusCode() != HttpStatus.OK ||
                !response.hasBody()) {
            throw new OAuthTokenNotValidException("Cannot retrieve user info from GOOGLE Auth server");
        }

        String body = response.getBody();
        try {
            JSONObject jsonBody = (JSONObject) new JSONParser().parse(body);
            String email = (String) jsonBody.get("email");

            return new OAuth2Response(OAUTH_TOKEN_PREFIX, email, response.getStatusCode());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new OAuthTokenNotValidException("Cannot retrieve user info from GOOGLE Auth server, user info is not parcelable");
    }
}
