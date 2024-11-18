package com.example.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuccessController {

    private static final Logger logger = LoggerFactory.getLogger(SuccessController.class);

    @GetMapping("/success")
    public String showSuccessPage() {
        logger.info("Entering showSuccessPage method");
        logger.info("Exiting showSuccessPage method");
        return "success";
    }
}
