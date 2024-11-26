package com.baseballproject.baseballbat_be.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * packageName : com.baseballproject.baseballbat_be.security.jwt
 * fileName : JwtUtils
 * author : BALLBAT
 * date : 24. 11. 26.
 * description : TODO: JwtUtils 파일
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 26.         BALLBAT          최초 생성
 */
@Component
public class JwtUtils {
    @Value("${jwt.secret_key}")
    private String secretKey;

    @Value("${jwt.expiration_time}")
    private long expirationTime;

    /**
     * 사용자명을 기반으로 JWT 토큰을 생성합니다.
     *
     * @param username 사용자명
     * @return 생성된 JWT 토큰
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * JWT 토큰에서 사용자명을 추출합니다.
     *
     * @param token JWT 토큰
     * @return 사용자명
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * JWT 토큰이 유효한지 확인합니다.
     *
     * @param token    JWT 토큰
     * @param username 사용자명
     * @return 유효 여부
     */
    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    /**
     * JWT 토큰의 만료 여부를 확인합니다.
     *
     * @param token JWT 토큰
     * @return 만료 여부
     */
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    /**
     * JWT 토큰에서 모든 클레임을 추출합니다.
     *
     * @param token JWT 토큰
     * @return 클레임
     */
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
