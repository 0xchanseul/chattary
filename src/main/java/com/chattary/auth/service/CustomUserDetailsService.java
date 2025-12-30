package com.chattary.auth.service;

import com.chattary.auth.entity.Auth;
import com.chattary.auth.repository.AuthRepository;
import com.chattary.config.AuthUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    public CustomUserDetailsService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        // 4-1️⃣ DB 조회
        Auth auth = authRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("invalid"));

        log.debug("pw='{}', len={}, startsWith$2={}",
                auth.getPassword(),
                auth.getPassword() == null ? -1 : auth.getPassword().length(),
                auth.getPassword() != null && auth.getPassword().startsWith("$2")
        );


        // 4-2️⃣ Security가 이해할 수 있는 형태로 변환
        return new AuthUserDetails(auth);
    }
}
