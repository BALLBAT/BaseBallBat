package com.baseballproject.baseballbat_be.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

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

    @Value("${jwt.expiration_time}")
    private long expirationTime;


    /**
     * JWT 토큰 생성
     *
     * @param claims JWT에 포함할 클레임 (key-value)
     * @return 생성된 JWT 토큰
     */
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * JWT 토큰에서 특정 클레임 값 추출
     *
     * @param token JWT 토큰
     * @param key   클레임 키
     * @return 클레임 값
     */
    public Object extractClaim(String token, String key) {
        return extractAllClaims(token).get(key);
    }

    /**
     * JWT 토큰에서 이메일 추출
     *
     * @param token JWT 토큰
     * @return 이메일
     */
    public String extractEmail(String token) {
        return (String) extractClaim(token, "email");
    }

    /**
     * JWT 토큰에서 소셜 제공자 추출
     *
     * @param token JWT 토큰
     * @return 소셜 제공자 (provider)
     */
    public String extractProvider(String token) {
        return (String) extractClaim(token, "provider");
    }

    /**
     * JWT 토큰의 유효성 검증
     *
     * @param token JWT 토큰
     * @return 유효 여부
     */
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * JWT 토큰의 만료 여부 확인
     *
     * @param token JWT 토큰
     * @return 만료 여부
     */
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    /**
     * JWT 토큰에서 모든 클레임 추출
     *
     * @param token JWT 토큰
     * @return 클레임
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
