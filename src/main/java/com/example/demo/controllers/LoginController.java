package com.example.demo.controllers;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        logger.info("Entering showLoginForm method");
        model.addAttribute("loginDTO", new LoginDTO());
        logger.info("Exiting showLoginForm method");
        return "login";
    }

    @PostMapping("/performlogin")
    public String performLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO, Model model) {
        logger.info("Entering performLogin method with user: {}", loginDTO.getUsername());
        String loginResult = loginService.loginUser(loginDTO);

        if ("Login successful!".equals(loginResult)) {
            logger.info("Login successful for user: {}", loginDTO.getUsername());
            return "redirect:/home";
        } else {
            logger.error("Login failed for user: {}", loginDTO.getUsername());
            model.addAttribute("loginError", loginResult);
            return "login";
        }
    }
}
