package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
        model.addAttribute("username", oauth2User.getAttribute("username"));
        model.addAttribute("avatarUrl", oauth2User.getAttribute("avatar"));
        model.addAttribute("discriminator", oauth2User.getAttribute("discriminator"));
        return "dashboard";
    }
}
