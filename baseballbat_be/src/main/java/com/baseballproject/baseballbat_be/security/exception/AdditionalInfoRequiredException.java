package com.baseballproject.baseballbat_be.security.exception;

/**
 * packageName : com.baseballproject.baseballbat_be.security.etc
 * fileName : AdditionalInfoRequiredException
 * author : BALLBAT
 * date : 25. 1. 8.
 * description : TODO: 소셜 로그인 중 추가 정보 입력이 필요한 경우 발생하는 예외
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 8.         BALLBAT          최초 생성
 */
public class AdditionalInfoRequiredException extends RuntimeException {
    private final String email;
    private final String socialId;
    private final String provider;

    public AdditionalInfoRequiredException(String email, String socialId, String provider) {
        super("Additional info required for social login.");
        this.email = email;
        this.socialId = socialId;
        this.provider = provider;
    }

    public String getEmail() {
        return email;
    }

    public String getSocialId() {
        return socialId;
    }

    public String getProvider() {
        return provider;
    }
}
