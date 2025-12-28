package com.chattary.auth.controller;

import com.chattary.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.chattary.auth.dto.AuthRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

@RequiredArgsConstructor // final을 위한 생성자 자동 생성
@Controller @Slf4j
public class AuthController {

    private final AuthService authService;
    private final MessageSource messageSource;

    @GetMapping("/signInPage")
    public String signInPage() {

        return "auth/signIn";
    }

    @GetMapping("/signUpPage")
    public String signUpPage() {

        return "auth/signUp";
    }

    @PostMapping("/signUp")
    public String signup(@Valid @ModelAttribute("signupRequest") AuthRequest authReq,
                         BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes,
                         HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "users/signup";
        }

        try {
            authService.signup(authReq);

            Locale locale = RequestContextUtils.getLocale(request); // 현재 요청의 locale
            String msg = messageSource.getMessage("auth.signup.success", null, locale);

            redirectAttributes.addFlashAttribute("successMessage", msg);

            return "redirect:/login";

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "users/signup";
        }

    }
}
