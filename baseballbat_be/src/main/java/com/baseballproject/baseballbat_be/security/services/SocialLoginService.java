package com.baseballproject.baseballbat_be.security.services;

import com.baseballproject.baseballbat_be.model.entity.auth.SocialUser;
import com.baseballproject.baseballbat_be.model.entity.auth.User;
import com.baseballproject.baseballbat_be.repository.user.SocialUserRepository;
import com.baseballproject.baseballbat_be.repository.user.UserRepository;
import com.baseballproject.baseballbat_be.security.exception.AdditionalInfoRequiredException;
import com.baseballproject.baseballbat_be.security.jwt.JwtProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
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

    @Value("${jwt.secret_key}")
    private String secretKey;

    @Value("${jwt.expiration_time}")
    private long expirationTime;

    private final RestTemplate restTemplate;
    private final SocialUserRepository socialUserRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    /**
     * 네이버 소셜 로그인 처리
     *
     * @param code  네이버로부터 받은 인증 코드
     * @param state 네이버로부터 받은 상태값 (CSRF 방지용)
     * @return JWT 토큰
     */
    public String naverLogin(String code, String state) throws Exception {
        // 1. 네이버 액세스 토큰 요청
        String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code"
                + "&client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&code=" + code
                + "&state=" + state;

        ResponseEntity<Map> tokenResponse = restTemplate.getForEntity(tokenUrl, Map.class);
        Map<String, Object> tokenBody = tokenResponse.getBody();

        if (tokenBody == null || tokenBody.get("access_token") == null) {
            throw new Exception("Failed to retrieve access token from Naver.");
        }

        String accessToken = (String) tokenBody.get("access_token");

        // 2. 네이버 사용자 정보 요청
        String userInfoUrl = "https://openapi.naver.com/v1/nid/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> userResponse = restTemplate.postForEntity(userInfoUrl, entity, Map.class);
        Map<String, Object> userBody = userResponse.getBody();

        if (userBody == null || userBody.get("response") == null) {
            throw new Exception("Failed to retrieve user info from Naver.");
        }

        Map<String, Object> userInfo = (Map<String, Object>) userBody.get("response");
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");
        String socialId = (String) userInfo.get("id"); // 네이버 사용자 고유 ID
        String snsProvider = "NAVER";

        // 3. 기존 계정 연동 또는 추가 정보 입력 처리
        User user = findOrCreateUserWithSocialAccount(email, name, socialId, snsProvider);

        // 4. JWT 생성
        return generateJwtToken(user);
    }

    /**
     * 기존 계정을 찾거나 추가 정보 입력 유도
     */
//    private User findOrCreateUserWithSocialAccount(String email, String name, String socialId, String snsProvider) {
//        // 1. 기존 User 찾기
//        Optional<User> existingUser = userRepository.findByEmail(email);
//
//        if (existingUser.isPresent()) {
//            User user = existingUser.get();
//
//            // 2. 소셜 계정 연동
//            linkSocialUserIfNotLinked(user, email, name, socialId, snsProvider);
//            return user; // 연동된 계정 반환
//        } else {
//            // 3. 기존 User가 없으면 추가 정보 입력 유도
//            throw new AdditionalInfoRequiredException(email, socialId, snsProvider);
//        }
//    }

//    private void linkSocialUserIfNotLinked(User user, String email, String name, String socialId, String snsProvider) {
//        Optional<SocialUser> linkedSocialUser = socialUserRepository.findBySocialIdAndProvider(socialId, snsProvider);
//
//        if (linkedSocialUser.isEmpty()) {
//            // 연동되지 않은 경우 소셜 계정 연동
//            SocialUser socialUser = new SocialUser();
//            socialUser.setEmail(email);
//            socialUser.setRealname(name);
//            socialUser.setSocialId(socialId);
//            socialUser.setProvider(snsProvider);
//            socialUser.setUser(user);
//            socialUserRepository.save(socialUser);
//        }
//    }

    /**
     * JWT 토큰 생성
     */
    private String generateJwtToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("realname", user.getRealname())
                .claim("username", user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}
