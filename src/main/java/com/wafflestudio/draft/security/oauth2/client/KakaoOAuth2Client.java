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
public class KakaoOAuth2Client implements OAuth2Client {
    public static final String OAUTH_TOKEN_PREFIX = "KAKAO";

//    Request Example
//
//    GET/POST /v2/user/me HTTP/1.1
//    Host: kapi.kakao.com
//    Authorization: Bearer {access_token}
//    Content-type: application/x-www-form-urlencoded;charset=utf-8

    private static final String KAKAO_HOST = "https://kapi.kakao.com";
    private static final String TOKEN_PREFIX = "Bearer";

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

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(KAKAO_HOST + "/v2/user/me");
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", TOKEN_PREFIX + " " + accessToken);
        headers.add("property_keys", "[\"kakao_account.email\"]");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                template.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, String.class);

        if (response.getStatusCode() != HttpStatus.OK ||
                !response.hasBody()) {
            throw new OAuthTokenNotValidException("Cannot retrieve user info from KAKAO Auth server");

        }

        String body = response.getBody();
        try {
            JSONObject jsonBody = (JSONObject) ((JSONObject) new JSONParser().parse(body)).get("kakao_account");
            String email = (String) jsonBody.get("email");

            return new OAuth2Response(OAUTH_TOKEN_PREFIX, email, response.getStatusCode());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        throw new OAuthTokenNotValidException("Cannot retrieve user info from KAKAO Auth server, user info is not parcelable");
    }
}
