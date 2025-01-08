package com.baseballproject.baseballbat_be.security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName : com.baseballproject.baseballbat_be.security.exception
 * fileName : GlobalExceptionHandler
 * author : BALLBAT
 * date : 25. 1. 8.
 * description : TODO: 전역 예외 처리 핸들러
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 8.         BALLBAT          최초 생성
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * AdditionalInfoRequiredException 처리
     */
    @ExceptionHandler(AdditionalInfoRequiredException.class)
    public ResponseEntity<?> handleAdditionalInfoRequiredException(AdditionalInfoRequiredException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "Additional info required for social login.");
        response.put("email", ex.getEmail());
        response.put("socialId", ex.getSocialId());
        response.put("provider", ex.getProvider());

        return ResponseEntity.status(400).body(response);
    }

    /**
     * 일반적인 Exception 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", ex.getMessage());

        return ResponseEntity.status(500).body(response);
    }
}
