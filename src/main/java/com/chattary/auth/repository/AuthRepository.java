package com.chattary.auth.repository;

import com.chattary.auth.entity.Auth;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    boolean existsByUserMail(String userMail);

    boolean existsByUserId(String userId);

    Optional<Auth> findByUserId(String userId);
}
