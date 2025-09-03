package com.example.moneytogether1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/secure")
    public ResponseEntity<String> secureEndpoint() {
        return ResponseEntity.ok("JWT 인증 성공!");
    }
}
