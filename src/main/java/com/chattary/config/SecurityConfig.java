package com.chattary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 폼로그인 쓸 거면 나중에 켜는 방향 추천(일단 개발용)
                .authorizeHttpRequests(auth -> auth
                        // 정적 리소스
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico").permitAll()

                        // 공개 페이지
                        .requestMatchers("/", "/signInPage", "/signUpPage", "/signUp", "/error", "/signIn").permitAll()

                        // 나머지
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/signInPage")              // 네가 만든 로그인 페이지 URL
                        .loginProcessingUrl("/signIn")     // 로그인 POST 처리 URL (form action)
                        .usernameParameter("userId")   // 폼 name에 맞추기
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)     // 성공 시 이동
                        .failureUrl("/signInPage?error=true")  // 실패 시
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }

}
