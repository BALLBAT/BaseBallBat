package com.baseballproject.baseballbat_be.security.contoller;

import com.baseballproject.baseballbat_be.security.services.NaverLoginService;
import com.baseballproject.baseballbat_be.security.services.SocialLoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * packageName : com.baseballproject.baseballbat_be.security.contoller
 * fileName : NaverLoginController
 * author : BALLBAT
 * date : 25. 1. 15.
 * description : TODO: 네이버 소셜 로그인 처리 컨트롤러
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 10.         BALLBAT          최초 생성
 * 25. 1. 15.         BALLBAT          네이버 로그인 콜백 로직 수정 및 쿠키 설정 추가
 */
@RestController
@RequestMapping("/api/auth/naver")
@RequiredArgsConstructor
@Slf4j
public class NaverLoginController {

    // 네이버 로그인 관련 서비스
    private final NaverLoginService naverLoginService;

    // 소셜 로그인 처리 서비스
    private final SocialLoginService socialLoginService;

    /**
     * 네이버 로그인 콜백 메서드
     *
     * @param code  네이버 OAuth 인증 코드
     * @param state 네이버 OAuth 상태 값 (CSRF 방지용)
     * @param response HTTP 응답 객체 (쿠키 설정 및 리디렉션 처리)
     */
    @GetMapping("/callback")
    public void naverCallback(
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            HttpServletResponse response) {
        log.info("Received Naver login callback with code={} and state={}", code, state);

        try {
            // 1. 네이버 API를 호출하여 사용자 정보 가져오기
            Map<String, Object> naverUserInfo = naverLoginService.getUserInfoFromCallback(code, state);

            // 2. 소셜 로그인 처리 및 JWT 토큰 생성
            String jwtToken = socialLoginService.processSocialLogin(
                    (String) naverUserInfo.get("email"),  // 사용자 이메일
                    (String) naverUserInfo.get("name"),   // 사용자 이름
                    (String) naverUserInfo.get("id"),     // 네이버 고유 ID
                    "NAVER"                              // 소셜 로그인 제공자
            );

            // 3. JWT를 쿠키에 설정
            ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwtToken)
                    .httpOnly(true)       // JavaScript에서 접근 불가
                    .secure(false)        // HTTPS 사용 시 true
                    .path("/")            // 모든 경로에서 쿠키 접근 가능
                    .sameSite("Lax")      // SameSite 설정
                    .maxAge(24 * 60 * 60) // 유효 기간 1일
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());


            // 4. 메인 페이지로 리디렉션
            log.info("Redirecting to main page with JWT set in cookie.");
            response.sendRedirect("http://localhost:8080/main");

        } catch (Exception e) {
            // 에러 발생 시 로그 기록 및 로그인 페이지로 리디렉션
            log.error("Error during Naver login callback", e);

            try {
                response.sendRedirect("http://localhost:8080/login?error=" + e.getMessage());
            } catch (IOException ex) {
                log.error("Failed to redirect to login page", ex);
            }
        }
    }
}
