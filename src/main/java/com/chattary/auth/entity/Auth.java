package com.chattary.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_tb")
@Getter @Setter
public class Auth {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false, unique=true, length=50)
    private String userId;

    @Column(nullable=false, unique=true, length=50)
    private String userMail;

    @Column(nullable=false, length=100)
    private String password;

    @Column(nullable=false, length=30)
    private String userNm;

    @Column(nullable=false)
    private LocalDateTime joinDt = LocalDateTime.now();

    @Column(nullable=true)
    private String level;

    protected Auth() { } // JPA 기본 생성자

    public Auth(String userId, String userMail, String userNm, String password) {
        this.userId = userId;
        this.userMail = userMail;
        this.userNm = userNm;
        this.password = password;
    }

}
