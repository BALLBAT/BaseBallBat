package com.baseballproject.baseballbat_be.model.entity.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * packageName : com.baseballproject.baseballbat_be.model.entity.auth
 * fileName : User
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
@Entity
@Table(name = "TB_USER")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_USER_SEQ_GEN")
    @SequenceGenerator(name = "TB_USER_SEQ_GEN", sequenceName = "TB_USER_SEQ", allocationSize = 1)
    private Long userId;

    @Column(name = "REALNAME", nullable = false)
    private String realname;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "BIRTHDATE")
    private String birthdate;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DETAIL_ADDRESS")
    private String detailAddress;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "ID_DELETED", length = 1)
    private String idDeleted = "N";

    @Column(name = "DELETED_AT")
    private java.sql.Timestamp deletedAt;

    @Column(name = "PASSWORD", nullable = false)
    private String password;
}
