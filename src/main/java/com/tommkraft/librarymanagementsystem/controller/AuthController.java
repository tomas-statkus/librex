package com.tommkraft.librarymanagementsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal());

        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", isAuthenticated);
        return ResponseEntity.ok(response);
    }
}
