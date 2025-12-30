package com.chattary.config;

import com.chattary.auth.entity.Auth;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class AuthUserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final Auth auth;

    public AuthUserDetails(Auth auth) {
        this.auth = auth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public @Nullable String getPassword() {
        return auth.getPassword();
    }

    @Override
    public String getUsername() {
        return auth.getUserId(); // 로그인 아이디 반환
    }

    public String getNickname() {
        return auth.getUserNm();
    }

    public String getLevel(){
        return auth.getLevel();
    }

    public LocalDateTime getJoinDt(){
        return auth.getJoinDt();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
