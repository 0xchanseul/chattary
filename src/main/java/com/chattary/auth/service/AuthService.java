package com.chattary.auth.service;

import com.chattary.auth.dto.AuthRequest;
import com.chattary.auth.entity.Auth;
import com.chattary.auth.repository.AuthRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor // final을 위한 생성자 자동 생성
@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    // private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();
    private final AuthenticationConfiguration authenticationConfiguration;

    @Transactional
    public String signup(AuthRequest req) {
        if (authRepository.existsByUserMail(req.getUserMail())) {
            throw new IllegalArgumentException("mail");
        } else if (authRepository.existsByUserId(req.getUserId())){
            throw new IllegalArgumentException("id");
        }

        String password = passwordEncoder.encode(req.getPassword());
        Auth saved = authRepository.save(new Auth(req.getUserId(), req.getUserMail(), req.getUserNm(), password));
        return saved.getUserId();
    }

    public void signin(AuthRequest req,
                         HttpServletRequest request,
                         HttpServletResponse response) {

        AuthenticationManager authenticationManager =
                authenticationConfiguration.getAuthenticationManager();

        // 1️⃣ 로그인 시도 (id, pw)
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                req.getUserId(),
                                req.getPassword()
                        )
                );

        // 2️⃣ 인증 성공 → SecurityContext 생성
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        // 3️⃣ 세션에 SecurityContext 저장 (★ 로그인 유지의 핵심)
        securityContextRepository.saveContext(context, request, response);
    }

}
