package com.baseballproject.baseballbat_be.model.social;

import lombok.Data;
import org.eclipse.angus.mail.iap.Response;

/**
 * packageName : com.baseballproject.baseballbat_be.model.social
 * fileName : NaverUserResponse
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
public class NaverUserResponse {
    private Response response;

    @Data
    public static class Response {
        private String id;
        private String email;
        private String name;
    }

    public String getEmail() {
        return response.email;
    }

    public String getName() {
        return response.name;
    }
}
