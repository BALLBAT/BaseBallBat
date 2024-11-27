package com.baseballproject.baseballbat_be.security.services;

import com.baseballproject.baseballbat_be.model.entity.auth.User;
import com.baseballproject.baseballbat_be.model.social.NaverTokenResponse;
import com.baseballproject.baseballbat_be.model.social.NaverUserResponse;
import com.baseballproject.baseballbat_be.repository.user.UserRepository;
import com.baseballproject.baseballbat_be.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * packageName : com.baseballproject.baseballbat_be.security.services
 * fileName : SocialLoginService
 * author : BALLBAT
 * date : 24. 11. 27.
 * description : TODO: 소셜 로그인 서비스
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 27.         BALLBAT          최초 생성
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SocialLoginService {

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    @Value("${naver.redirect.uri}")
    private String redirectUri;

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public String naverLogin(String code, String state) {
        // 네이버 토큰 발급 API 호출
        String accessToken = getNaverAccessToken(code, state);

        // 네이버 사용자 정보 조회
        NaverUserResponse userInfo = getNaverUserInfo(accessToken);

        // 사용자 정보 처리 및 JWT 발급
        Optional<User> userOptional = userRepository.findByEmail(userInfo.getEmail());
        User user = userOptional.orElseGet(() -> registerNewUser(userInfo));

        // JWT 토큰 생성 후 반환
        return jwtUtils.generateToken(user.getUsername());
    }

    private String getNaverAccessToken(String code, String state) {
        // 네이버의 액세스 토큰을 얻기 위해 토큰 발급 API를 호출
        String tokenUri = "https://nid.naver.com/oauth2.0/token?client_id=" + clientId
                + "&client_secret=" + clientSecret + "&grant_type=authorization_code"
                + "&code=" + code + "&state=" + state + "&redirect_uri=" + redirectUri;

        ResponseEntity<NaverTokenResponse> response = restTemplate.getForEntity(tokenUri, NaverTokenResponse.class);
        return response.getBody().getAccessToken();
    }

    private NaverUserResponse getNaverUserInfo(String accessToken) {
        // 네이버 사용자 정보를 가져오기 위해 네이버 사용자 정보 API를 호출
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<NaverUserResponse> response = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me", HttpMethod.GET, entity, NaverUserResponse.class);

        return response.getBody();
    }

    private User registerNewUser(NaverUserResponse userInfo) {
        // 새로운 사용자 등록 로직
        User user = new User();
        user.setUsername(userInfo.getEmail());
        user.setRealname(userInfo.getName());
        user.setEmail(userInfo.getEmail());
        // 기타 사용자 정보 설정
        return userRepository.save(user);
    }
}
