package com.chattary.main.controller;

import com.chattary.config.AuthUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal AuthUserDetails user, Model model) {

        if (user != null) {
            model.addAttribute("nickname", user.getUsername());
            model.addAttribute("level", user.getLevel());
        }

        return "index";
    }
}
