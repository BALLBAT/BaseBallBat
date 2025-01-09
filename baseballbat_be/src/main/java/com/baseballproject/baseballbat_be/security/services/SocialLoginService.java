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
                return generateJwtToken(user, provider);
            } else {
                log.warn("Social account not linked for email={}", email);
                throw new AdditionalInfoRequiredException(email, socialId, provider);
            }
        }

        // 3. 신규 사용자 처리
        log.warn("No user found for email={}, additional info required.", email);
        throw new AdditionalInfoRequiredException(email, socialId, provider);
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

        return jwtProvider.generateToken(claims);
    }
}
