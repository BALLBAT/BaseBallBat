package com.baseballproject.baseballbat_be.model.social;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * packageName : com.baseballproject.baseballbat_be.model.social
 * fileName : NaverTokenResponse
 * author : BALLBAT
 * date : 24. 11. 27.
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 27.         BALLBAT          최초 생성
 */
@Data
public class NaverTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private String expiresIn;

    @JsonProperty("error")
    private String error;

    @JsonProperty("error_description")
    private String errorDescription;
}
