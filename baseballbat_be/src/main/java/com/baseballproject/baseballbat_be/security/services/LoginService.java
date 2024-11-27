package com.baseballproject.baseballbat_be.security.services;

import com.baseballproject.baseballbat_be.model.entity.auth.User;
import com.baseballproject.baseballbat_be.repository.user.UserRepository;
import com.baseballproject.baseballbat_be.security.dto.LoginReq;
import com.baseballproject.baseballbat_be.security.dto.LoginRes;
import com.baseballproject.baseballbat_be.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName : com.baseballproject.baseballbat_be.service.user
 * fileName : LoginService
 * author : BALLBAT
 * date : 24. 11. 26.
 * description : TODO: Login Service
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 26.         BALLBAT          최초 생성
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    /**
     * 사용자 로그인 처리를 수행합니다.
     *
     * @param loginReq 사용자 로그인 요청 DTO (username과 password를 포함)
     * @return 로그인 결과 DTO (username과 JWT 토큰을 포함)
     * @throws IllegalArgumentException 사용자 이름이 없거나 비밀번호가 일치하지 않을 경우 발생
     */
    public LoginRes login(LoginReq loginReq) {
        // 사용자 이름으로 사용자 정보를 조회합니다.
        Optional<User> optionalUser = userRepository.findByUsername(loginReq.getUsername());
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("사용자가 존재하지 않습니다.");
        }

        User user = optionalUser.get();

        // 입력된 비밀번호와 저장된 비밀번호를 비교하기 전에 로그 출력
        log.debug("입력된 비밀번호: " + loginReq.getPassword());
        log.debug("DB에 저장된 비밀번호 (인코딩된 형태): " + user.getPassword());

        // 입력된 비밀번호와 저장된 비밀번호를 비교합니다.
        if (!passwordEncoder.matches(loginReq.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰을 생성하여 반환합니다.
        String token = jwtUtils.generateToken(user.getUsername());
        return new LoginRes(user.getUsername(), token);
    }
}
