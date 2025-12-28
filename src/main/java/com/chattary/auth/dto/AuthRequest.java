package com.chattary.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank @Size(min = 1, max = 30)
    private String nickname;

    @NotBlank @Size(min = 4, max = 50)
    private String password;

    @NotBlank @Size(min = 4, max = 50)
    private String ID;

}
