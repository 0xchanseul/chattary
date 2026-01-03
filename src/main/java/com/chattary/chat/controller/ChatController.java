package com.chattary.chat.controller;

import com.chattary.config.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor // final을 위한 생성자 자동 생성
@Controller
@Slf4j
public class ChatController {

    @GetMapping("/chatMainPage")
    public String chatMainPage(@AuthenticationPrincipal AuthUserDetails user) {

        if(user.getLevel() == null || user.getLevel().equals("")){
            return "redirect:/selectLevelPage";
        }

        return "chat/chatMain";
    }

    @GetMapping("/selectLevelPage")
    public String selectLevelPage() {

        return "chat/selectLevel";
    }

}
