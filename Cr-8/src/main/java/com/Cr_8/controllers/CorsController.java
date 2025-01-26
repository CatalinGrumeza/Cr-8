package com.Cr_8.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorsController {
	@GetMapping("/csrf-token")
    public Map<String, String> csrf(CsrfToken token) {
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token.getToken());
        return tokenMap; // Return the CSRF token as a JSON object
    }
	}


