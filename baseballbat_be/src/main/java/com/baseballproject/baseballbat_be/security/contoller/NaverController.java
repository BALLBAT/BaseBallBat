package com.baseballproject.baseballbat_be.security.contoller;

import com.baseballproject.baseballbat_be.security.services.SocialLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName : com.baseballproject.baseballbat_be.security.contoller
 * fileName : NaverController
 * author : BALLBAT
 * date : 24. 11. 27.
 * description : TODO: 네이버 로그인 컨트롤러
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 27.         BALLBAT          최초 생성
 */
@RestController
@RequestMapping("/api/naver")
@RequiredArgsConstructor
@Slf4j
public class NaverController {
    private final SocialLoginService socialLoginService;

    /**
     * 네이버 로그인 콜백을 처리합니다.
     * 네이버로부터 인증 코드를 받고, 이를 사용해 액세스 토큰 및 사용자 정보를 조회합니다.
     * @param code 네이버로부터 받은 인증 코드
     * @param state 네이버로부터 받은 상태값 (CSRF 방지용)
     * @return JWT 토큰
     */
    @GetMapping("/callback")
    public ResponseEntity<?> naverCallback(@RequestParam String code, @RequestParam String state) {
        log.info("Received code: {}, state: {}", code, state);
        try {
            // 소셜 로그인 서비스 호출
            String jwtToken = socialLoginService.naverLogin(code, state);
            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            log.error("Error during Naver login: ", e);
            return ResponseEntity.status(500).body("네이버 로그인에 실패하였습니다.");
        }
    }
}
