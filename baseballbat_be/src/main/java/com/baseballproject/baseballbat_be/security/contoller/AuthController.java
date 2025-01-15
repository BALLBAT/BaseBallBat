package com.baseballproject.baseballbat_be.security.contoller;

import com.baseballproject.baseballbat_be.security.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * packageName : com.baseballproject.baseballbat_be.security.contoller
 * fileName : AuthController
 * author : BALLBAT
 * date : 25. 1. 15.
 * description : TODO: JWT 유효성 검증을 위한 앤드 포인트
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 15.         BALLBAT          최초 생성
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final JwtProvider jwtProvider;

    /**
     * JWT 유효성 검증
     * @param request HTTP 요청 (쿠키에서 JWT 추출)
     * @return 유효한 경우 사용자 정보 반환
     */
    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        if (token == null) {
            return ResponseEntity.badRequest().body("JWT 토큰이 없습니다.");
        }

        boolean isValid = jwtProvider.validateToken(token);
        if (isValid) {
            return ResponseEntity.ok("토큰이 유효합니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 유효하지 않거나 만료되었습니다.");
        }
    }
}
