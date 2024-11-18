package com.example.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
public class CustomErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public String handleError(Model model) {
        logger.info("Entering handleError method");
        model.addAttribute("message", "The page you are looking for might have been removed, had its name changed, or is temporarily unavailable.");
        logger.info("Exiting handleError method");
        return "error";
    }

}
