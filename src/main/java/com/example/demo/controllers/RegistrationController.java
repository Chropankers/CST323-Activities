package com.example.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.DTO.UserDTO;
import com.example.demo.models.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        logger.info("Entering showRegistrationForm method");
        model.addAttribute("user", new UserDTO());
        logger.info("Exiting showRegistrationForm method");
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result) {
        logger.info("Entering registerUser method with user: {}", userDTO.getUsername());
        if (result.hasErrors()) {
            logger.error("Validation failed for user: {}", userDTO.getUsername());
            return "register";
        }

        User user = userService.convertToEntity(userDTO);
        userService.saveUser(user);

        logger.info("User registration successful for user: {}", userDTO.getUsername());
        return "redirect:/success";
    }
}
