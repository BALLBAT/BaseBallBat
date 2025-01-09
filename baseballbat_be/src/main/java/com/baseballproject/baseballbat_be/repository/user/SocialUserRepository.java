package com.baseballproject.baseballbat_be.repository.user;

import com.baseballproject.baseballbat_be.model.entity.auth.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
* packageName : com.baseballproject.baseballbat_be.repository.user 
* fileName : SocialUserRepository 
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
public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {
    // 소셜 ID와 Provider로 SocialUser 검색
    Optional<SocialUser> findByProviderAndSocialId(String provider, String socialId);

    // 이메일로 SocialUser 검색
    Optional<SocialUser> findByEmail(String email);

    // User ID로 모든 SocialUser 검색
    Optional<SocialUser> findByUser_UserId(Long userId);
}
