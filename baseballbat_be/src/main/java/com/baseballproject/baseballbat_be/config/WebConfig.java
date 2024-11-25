package com.baseballproject.baseballbat_be.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
* packageName : com.baseballproject.baseballbat_be.config 
* fileName : WebConfig 
* author : BALLBAT 
* date : 24. 11. 25. 
* description : 
* 요약 :
*
* =========================================================== 
* DATE            AUTHOR             NOTE 
* ----------------------------------------------------------- 
* 24. 11. 25.         BALLBAT          최초 생성 
*/
@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // 모든 경로에 대해 CORS 허용
                        .allowedOrigins("http://localhost:8080")  // 허용할 출처
                        .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 메서드
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
