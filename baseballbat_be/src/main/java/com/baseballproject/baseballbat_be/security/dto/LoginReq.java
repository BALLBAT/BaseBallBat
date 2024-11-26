package com.baseballproject.baseballbat_be.security.dto;

import lombok.*;

/**
 * packageName : com.baseballproject.baseballbat_be.model.dto.auth.login
 * fileName : LoginReq
 * author : BALLBAT
 * date : 24. 11. 26.
 * description : TODO: 로그인 요청 DTO
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 26.         BALLBAT          최초 생성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {
    private String username;
    private String password;
}
