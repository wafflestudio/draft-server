package com.wafflestudio.draft.security.oauth2.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

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

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", TOKEN_PREFIX + " " + accessToken);
        headers.add("property_keys", "[\"kakao_account.email\"]");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
        ParameterizedTypeReference<HashMap<String, Object>> responseType =
                new ParameterizedTypeReference<HashMap<String, Object>>() {
                };

        ResponseEntity<HashMap<String, Object>> response =
                template.exchange(KAKAO_HOST + "/v2/user/me", HttpMethod.GET, entity, responseType);

        if (response.getStatusCode() != HttpStatus.OK ||
                !response.hasBody() ||
                !response.getBody().containsKey("kakao_account") ||
                !(response.getBody().get("kakao_account") instanceof HashMap)) {
            throw new UsernameNotFoundException("Cannot retrieve user info from KAKAO Auth server");
        }

        HashMap<String, Object> account = (HashMap<String, Object>) response.getBody().get("kakao_account");

        if (!(boolean) account.get("is_email_valid")) {
            throw new UsernameNotFoundException("Email information not exist or not agreed");
        }

        System.out.println(account.get("email").toString());
        System.out.println(response.toString());


        // TODO: Make up kakao response
        return new OAuth2Response(OAUTH_TOKEN_PREFIX, account.get("email").toString(), response.getStatusCode());
    }
}
