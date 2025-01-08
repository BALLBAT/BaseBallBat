package com.baseballproject.baseballbat_be.model.entity.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName : com.baseballproject.baseballbat_be.model.entity.auth
 * fileName : SocialUser
 * author : BALLBAT
 * date : 25. 1. 8.
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 8.         BALLBAT          최초 생성
 */
@Entity
@Table(name = "TB_SOCIAL_USER", uniqueConstraints = {
        @UniqueConstraint(name = "UK_SOCIAL_PROVIDER_ID", columnNames = {"provider", "socialId"})
})
@Getter
@Setter
public class SocialUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SOCIAL_USER_ID")
    private Long socialUserId;

    @Column(name = "PROVIDER", nullable = false)
    private String provider;

    @Column(name = "SOCIAL_ID", nullable = false)
    private String socialId;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "REALNAME")
    private String realname;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

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
