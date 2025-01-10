package com.baseballproject.baseballbat_be.model.entity.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
* packageName : com.baseballproject.baseballbat_be.model.entity.auth 
* fileName : SocialUser 
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
@Entity
@Table(
        name = "TB_SOCIAL_USER",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_SOCIAL_PROVIDER_ID", columnNames = {"provider", "socialId"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class SocialUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SOCIAL_USER_ID")
    private Long socialUserId;

    @Column(name = "PROVIDER", nullable = false)
    private String provider; // 소셜 로그인 제공자 (e.g., NAVER, KAKAO)

    @Column(name = "SOCIAL_ID", nullable = false)
    private String socialId; // 소셜 플랫폼의 사용자 고유 ID

    @Column(name = "EMAIL", nullable = false)
    private String email; // 소셜 로그인 사용자의 이메일

    @Column(name = "REALNAME")
    private String realname; // 소셜 로그인 사용자의 이름

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "FK_SOCIAL_USER_USER"))
    @JsonIgnore
    private User user; // User와 연관관계

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
