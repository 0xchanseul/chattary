package com.chattary.auth.service;

import com.chattary.auth.dto.AuthRequest;
import com.chattary.auth.entity.Auth;
import com.chattary.auth.repository.AuthRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // final을 위한 생성자 자동 생성
@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public String signup(AuthRequest req) {
        if (authRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        String hash = passwordEncoder.encode(req.getPassword());
        Auth saved = authRepository.save(new Auth(req.getEmail(), req.getID(), req.getNickname(), hash));
        return saved.getID();
    }
}
