package com.baseballproject.baseballbat_be.security.contoller;

import com.baseballproject.baseballbat_be.security.services.SocialLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
     * 네이버 로그인 콜백 처리
     * @param code 네이버로부터 받은 인증 코드
     * @param state 네이버로부터 받은 상태값
     * @return JWT 토큰
     */
    @GetMapping("/callback")
    public ResponseEntity<?> naverCallback(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "state") String state) {
        log.info("Received code: {}, state: {}", code, state);

        try {
            // 소셜 로그인 서비스 호출
            String jwtToken = socialLoginService.naverLogin(code, state);

            // 추가 정보 입력 필요 여부 확인
            boolean needsAdditionalInfo = socialLoginService.needsAdditionalInfo(jwtToken);

            // 성공 응답 데이터 구성
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "네이버 로그인에 성공하였습니다.");
            response.put("token", jwtToken);
            response.put("needsAdditionalInfo", needsAdditionalInfo);

            log.info("Naver login successful: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error during Naver login: ", e);

            // 에러 응답 데이터 구성
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "네이버 로그인에 실패하였습니다.");
            errorResponse.put("details", e.getMessage());

            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
