package com.baseballproject.baseballbat_be.repository.user;

import com.baseballproject.baseballbat_be.model.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
* packageName : com.baseballproject.baseballbat_be.repository.user 
* fileName : UserRepository 
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
@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
