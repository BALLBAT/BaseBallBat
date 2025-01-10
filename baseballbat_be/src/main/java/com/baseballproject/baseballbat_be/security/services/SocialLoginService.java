package com.baseballproject.baseballbat_be.security.services;

import com.baseballproject.baseballbat_be.model.entity.auth.SocialUser;
import com.baseballproject.baseballbat_be.model.entity.auth.User;
import com.baseballproject.baseballbat_be.repository.user.SocialUserRepository;
import com.baseballproject.baseballbat_be.repository.user.UserRepository;
import com.baseballproject.baseballbat_be.security.exception.AdditionalInfoRequiredException;
import com.baseballproject.baseballbat_be.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
* packageName : com.baseballproject.baseballbat_be.security.services 
* fileName : SocialLoginService 
* author : BALLBAT 
* date : 25. 1. 9. 
* description : 
* 요약 :
*
* =========================================================== 
* DATE            AUTHOR             NOTE 
* ----------------------------------------------------------- 
* 25. 1. 9.         BALLBAT          최초 생성 
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class SocialLoginService {
    private final SocialUserRepository socialUserRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    /**
     * 소셜 로그인 처리
     *
     * @param email    소셜 계정 이메일
     * @param realName 소셜 계정 사용자 실명
     * @param socialId 소셜 계정 고유 ID
     * @param provider 소셜 제공자
     * @return JWT 토큰
     */
    public String processSocialLogin(String email, String realName, String socialId, String provider) {
        log.debug("Processing social login for email={}, realName={}, socialId={}, provider={}", email, realName, socialId, provider);

        // 1. 기존 사용자 확인
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            log.debug("Found existing user for email={}", email);

            // 2. 소셜 사용자 연동 확인
            Optional<SocialUser> optionalSocialUser = socialUserRepository.findByProviderAndSocialId(provider, socialId);

            if (optionalSocialUser.isPresent()) {
                log.debug("Social account already linked for email={}", email);

                // JWT 토큰 발급
                String jwtToken = generateJwtToken(user, provider);
                log.debug("Generated JWT Token for existing user: {}", jwtToken);

                return jwtToken;
            } else {
                log.warn("Social account not linked for email={}", email);
                linkSocialAccount(user, socialId, provider, realName);

                // JWT 토큰 발급
                String jwtToken = generateJwtToken(user, provider);
                log.debug("Generated JWT Token after linking social account: {}", jwtToken);

                return jwtToken;
            }
        }

        // 3. 신규 사용자 처리
        log.warn("No user found for email={}, creating new user.", email);
        String jwtToken = createUserWithSocialAccount(email, realName, socialId, provider);

        log.debug("Generated JWT Token for new user: {}", jwtToken);

        return jwtToken;
    }

    /**
     * 새로운 사용자 생성 및 소셜 계정 연동
     *
     * @param email    이메일
     * @param realName 실명
     * @param socialId 소셜 계정 ID
     * @param provider 소셜 제공자
     * @return 생성된 사용자의 JWT 토큰
     */
    private String createUserWithSocialAccount(String email, String realName, String socialId, String provider) {
        log.info("Creating new user with social account: email={}, provider={}", email, provider);

        // USERNAME 자동 생성 로직
        String generatedUsername = generateUniqueUsername(email);

        User user = new User();
        user.setEmail(email);
        user.setRealname(realName);
        user.setUsername(generatedUsername); // 자동 생성된 USERNAME 사용
        user.setPassword("SOCIAL_LOGIN"); // 소셜 로그인 기본 비밀번호 설정

        User savedUser = userRepository.save(user);

        linkSocialAccount(savedUser, socialId, provider, realName);
        log.info("User created and social account linked: {}", savedUser.getEmail());

        // JWT 토큰 발급
        String jwtToken = generateJwtToken(savedUser, provider);
        log.debug("Generated JWT Token for new user: {}", jwtToken);

        return jwtToken;
    }

    /**
     * 소셜 계정을 사용자와 연동
     *
     * @param user      사용자
     * @param socialId  소셜 계정 ID
     * @param provider  소셜 제공자
     * @param realName  실명
     */
    private void linkSocialAccount(User user, String socialId, String provider, String realName) {
        SocialUser socialUser = new SocialUser();
        socialUser.setUser(user);
        socialUser.setSocialId(socialId);
        socialUser.setProvider(provider);
        socialUser.setEmail(user.getEmail());
        socialUser.setRealname(realName);
        socialUserRepository.save(socialUser);
        log.debug("Linked social account with socialId={} and provider={} to user={}", socialId, provider, user.getEmail());
    }

    /**
     * JWT 토큰 생성
     *
     * @param user     사용자 엔티티
     * @param provider 소셜 제공자
     * @return JWT 토큰
     */
    private String generateJwtToken(User user, String provider) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("realName", user.getRealname());
        claims.put("provider", provider);

        String jwtToken = jwtProvider.generateToken(claims);

        // 디버깅 로그
        log.debug("Generated JWT Token: {}", jwtToken);

        return jwtToken;
    }

    /**
     * UNIQUE한 USERNAME 생성
     *
     * @param email 이메일
     * @return 고유한 USERNAME
     */
    private String generateUniqueUsername(String email) {
        String baseUsername = email.split("@")[0]; // "user1" from "user1@example.com"
        String uniqueUsername = baseUsername;
        int counter = 1;

        while (userRepository.existsByUsername(uniqueUsername)) {
            uniqueUsername = baseUsername + "_" + counter;
            counter++;
        }

        return uniqueUsername;
    }
}
