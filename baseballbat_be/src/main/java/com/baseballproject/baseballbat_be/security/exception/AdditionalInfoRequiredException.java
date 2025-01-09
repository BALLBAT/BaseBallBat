package com.baseballproject.baseballbat_be.security.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
* packageName : com.baseballproject.baseballbat_be.security.exception 
* fileName : AdditionalInfoRequiredException 
* author : BALLBAT 
* date : 25. 1. 9. 
* description : 
* 요약 :
*
* =========================================================== 
* DATE            AUTHOR             NOTE 
* ----------------------------------------------------------- 
* 25. 1. 9.         BALLBAT          최초 생성 
*/
@Getter
public class AdditionalInfoRequiredException extends RuntimeException {
    private final String email;
    private final String socialId;
    private final String provider;

    public AdditionalInfoRequiredException(String email, String socialId, String provider) {
        super("Additional information required for social login.");
        this.email = email;
        this.socialId = socialId;
        this.provider = provider;
    }
}
