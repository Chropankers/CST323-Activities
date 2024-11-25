package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.service.WasabiStorageService;

@RestController
@RequestMapping("/debug")
public class DebugController {

    @Autowired
    private WasabiStorageService wasabiStorageService;

    @GetMapping("/bucket")
    public ResponseEntity<String> testBucketConnection() {
        try {
            wasabiStorageService.testWasabiConnection();
            return ResponseEntity.ok("Bucket connection successful!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
