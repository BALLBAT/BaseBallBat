package com.baseballproject.baseballbat_be.security.contoller;

import com.baseballproject.baseballbat_be.security.services.NaverLoginService;
import com.baseballproject.baseballbat_be.security.services.SocialLoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName : com.baseballproject.baseballbat_be.security.contoller
 * fileName : NaverLoginController
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
@RestController
@RequestMapping("/api/auth/naver")
@RequiredArgsConstructor
@Slf4j
public class NaverLoginController {

    private final NaverLoginService naverLoginService;
    private final SocialLoginService socialLoginService;

    /**
     * 네이버 로그인 콜백
     * @param code  네이버 OAuth 인증 코드
     * @param state 네이버 OAuth 상태 값
     * @return 로그인 처리 결과 (JWT 토큰 또는 에러 메시지)
     */
    @GetMapping("/callback")
    public void naverCallback(
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            HttpServletResponse response) throws IOException {
        log.info("Received Naver login callback with code={} and state={}", code, state);

        try {
            // 네이버 API에서 사용자 정보 가져오기
            Map<String, Object> naverUserInfo = naverLoginService.getUserInfoFromCallback(code, state);

            // 소셜 로그인 처리
            String jwtToken = socialLoginService.processSocialLogin(
                    (String) naverUserInfo.get("email"),
                    (String) naverUserInfo.get("name"),
                    (String) naverUserInfo.get("id"),
                    "NAVER"
            );

            // JWT를 쿠키에 저장
            ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwtToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(24 * 60 * 60)
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

            // 메인 페이지로 리디렉션
            response.sendRedirect("http://localhost:8080/main");
        } catch (Exception e) {
            log.error("Error during Naver login callback", e);

            // 로그인 페이지로 리디렉션
            response.sendRedirect("http://localhost:8080/login?error=" + e.getMessage());
        }
    }
}
