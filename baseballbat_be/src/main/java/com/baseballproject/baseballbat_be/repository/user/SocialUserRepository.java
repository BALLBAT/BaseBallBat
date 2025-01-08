package com.baseballproject.baseballbat_be.repository.user;

import com.baseballproject.baseballbat_be.model.entity.auth.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
* packageName : com.baseballproject.baseballbat_be.repository.user 
* fileName : SocialUserRepository 
* author : BALLBAT 
* date : 25. 1. 8. 
* description : 
* 요약 :
*
* =========================================================== 
* DATE            AUTHOR             NOTE 
* ----------------------------------------------------------- 
* 25. 1. 8.         BALLBAT          최초 생성 
*/
@Repository
public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {
    Optional<SocialUser> findByProviderAndSocialId(String provider, String socialId);
    Optional<SocialUser> findByEmail(String email); // 이메일로 사용자 조회
}
