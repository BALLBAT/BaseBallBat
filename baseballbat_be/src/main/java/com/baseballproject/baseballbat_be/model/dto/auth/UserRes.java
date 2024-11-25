package com.baseballproject.baseballbat_be.model.dto.auth;

import lombok.*;

/**
 * packageName : com.baseballproject.baseballbat_be.model.dto.auth
 * fileName : UserRes
 * author : BALLBAT
 * date : 24. 11. 25.
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 11. 25.         BALLBAT          최초 생성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRes {
    private Long userId;     // 사용자 ID
    private String realname; // 실명 필드
    private String username; // 사용자 ID
    private String email;    // 이메일 주소
    private String birthdate; // 생년월일
    private String gender;   // 성별
    private String address;  // 주소
    private String detailAddress; // 상세 주소
    private String nickname; // 닉네임
    private String idDeleted; // 삭제 여부
    private java.sql.Timestamp deletedAt; // 삭제 시간
}
