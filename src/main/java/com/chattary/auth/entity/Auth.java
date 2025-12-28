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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String ID;

    @Column(nullable=false, unique=true, length=50)
    private String email;

    @Column(nullable=false, length=100)
    private String passwordHash;

    @Column(nullable=false, length=30)
    private String nickname;

    @Column(nullable=false)
    private LocalDateTime createdAt = LocalDateTime.now();

    protected Auth() { } // JPA 기본 생성자

    public Auth(String ID, String email, String nickname, String passwordHash) {
        this.ID = ID;
        this.email = email;
        this.nickname = nickname;
        this.passwordHash = passwordHash;
    }

}
