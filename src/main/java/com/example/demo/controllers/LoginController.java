package com.example.demo.controllers;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";  // This should return the login.html template
    }

    @PostMapping("/performlogin")
    public String performLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO, Model model) {
        String loginResult = loginService.loginUser(loginDTO);

        if ("Login successful!".equals(loginResult)) {
            return "redirect:/home";  // Redirect to the home page if login is successful
        } else {
            model.addAttribute("loginError", loginResult);
            return "login";  // Stay on the login page and show an error message if login failed
        }
    }
}
