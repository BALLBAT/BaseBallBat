package com.baseballproject.baseballbat_be.security.contoller;

import com.baseballproject.baseballbat_be.security.dto.LoginReq;
import com.baseballproject.baseballbat_be.security.dto.LoginRes;
import com.baseballproject.baseballbat_be.security.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName : com.baseballproject.baseballbat_be.security.contoller
 * fileName : LoginController
 * author : BALLBAT
 * date : 24. 11. 26.
 * description : TODO: Login Controller
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 26.         BALLBAT          최초 생성
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    /**
     * 사용자 로그인을 처리합니다.
     *
     * @param loginReq 사용자 로그인 요청 DTO (username과 password를 포함)
     * @return 로그인 결과 DTO (username과 JWT 토큰을 포함)
     */
    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq loginReq) {
        // 로그인 요청을 처리하고 결과를 반환합니다.
        LoginRes loginRes = loginService.login(loginReq);
        return ResponseEntity.ok(loginRes);
    }
}
