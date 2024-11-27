package com.baseballproject.baseballbat_be.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * packageName : com.baseballproject.baseballbat_be.config
 * fileName : AppConfig
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
@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
