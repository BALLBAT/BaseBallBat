package com.baseballproject.baseballbat_be.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * packageName : com.baseballproject.baseballbat_be.security.jwt
 * fileName : JwtProvider
 * author : BALLBAT
 * date : 25. 1. 8.
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 8.         BALLBAT          최초 생성
 */
@Component
public class JwtProvider {
    @Value("${jwt.secret_key}")
    private String secretKey;

    // JWT에서 이메일 추출
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // 이메일이 subject로 설정되어 있다고 가정
    }
}
