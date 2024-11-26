package com.baseballproject.baseballbat_be.repository.user;

import com.baseballproject.baseballbat_be.model.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // 이메일 중복 확인용 네이티브 쿼리 예시
//    @Query(value = "SELECT * FROM TB_USER WHERE EMAIL LIKE '%' || :email || '%'", nativeQuery = true)
//    Optional<User> findByEmail(@Param("email") String email);

    // 이메일 중복 확인용 네이티브 쿼리 예시
    @Query(value = "SELECT * FROM TB_USER WHERE EMAIL = :email", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);



    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM TB_USER WHERE EMAIL = :email", nativeQuery = true)
    boolean existsByEmail(@Param("email") String email);


}
