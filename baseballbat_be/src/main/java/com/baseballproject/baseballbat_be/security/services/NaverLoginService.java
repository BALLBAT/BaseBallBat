package com.baseballproject.baseballbat_be.security.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * packageName : com.baseballproject.baseballbat_be.security.services
 * fileName : NaverLoginService
 * author : BALLBAT
 * date : 25. 1. 10.
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 10.         BALLBAT          최초 생성
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NaverLoginService {
    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    @Value("${naver.token_url}")
    private String tokenUrl;

    @Value("${naver.user_info_url}")
    private String userInfoUrl;

    private final RestTemplate restTemplate;

    /**
     * 네이버 콜백에서 액세스 토큰과 사용자 정보를 가져옵니다.
     */
    public Map<String, Object> getUserInfoFromCallback(String code, String state) {
        String accessToken = getAccessToken(code, state);
        return getUserInfo(accessToken);
    }

    /**
     * 네이버 액세스 토큰 요청
     */
    public String getAccessToken(String code, String state) {
        String url = String.format(
                "%s?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&state=%s",
                tokenUrl, clientId, clientSecret, code, state
        );

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            log.error("Failed to get access token from Naver: {}", response);
            throw new IllegalStateException("네이버 액세스 토큰 요청 실패");
        }

        return (String) response.getBody().get("access_token");
    }

    /**
     * 네이버 사용자 정보 요청
     */
    public Map<String, Object> getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            log.error("Failed to get user info from Naver: {}", response);
            throw new IllegalStateException("네이버 사용자 정보 요청 실패");
        }

        return (Map<String, Object>) response.getBody().get("response");
    }
}
