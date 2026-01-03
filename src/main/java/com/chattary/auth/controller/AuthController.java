package com.chattary.auth.controller;

import com.chattary.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public String signInPage(@RequestParam(value = "error", required = false) String error,
                             Model model) {

        if (error != null) {

            model.addAttribute("loginFail", true);
        }

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
            return "auth/signUp";
        }

        Locale locale = RequestContextUtils.getLocale(request); // 현재 요청의 locale

        try {
            authService.signup(authReq);

            String msg = messageSource.getMessage("signup.success", null, locale);

            redirectAttributes.addFlashAttribute("successMessage", msg);

            return "redirect:/signInPage";

        } catch (IllegalArgumentException e) {

            String msg ="";

            if(e.getMessage().equals("mail")){
                msg = messageSource.getMessage("signup.duplicate.mail", null, locale);
            } else if(e.getMessage().equals("id")){
                msg = messageSource.getMessage("signup.duplicate.ID", null, locale);
            } else {
                msg = messageSource.getMessage("signup.fail", null, locale);
            }

            model.addAttribute("errorMessage", msg);

            return "auth/signUp";
        }

    }

    /*@PostMapping("/signIn")
    public String signin(@RequestBody AuthRequest req,
                         HttpServletRequest request,
                         HttpServletResponse response) {

        log.debug("here!!!!!");

        authService.signin(req, request, response);
        return "ok";
    }*/

}
