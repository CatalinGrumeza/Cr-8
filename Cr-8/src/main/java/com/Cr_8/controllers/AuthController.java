package com.Cr_8.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Cr_8.entities.Admin;
import com.Cr_8.servicies.AdminService;


@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String password,@RequestParam String email,@RequestParam String name) {
        Admin admin=new Admin();
        admin.setEmail(email);
        admin.setName(name);
        admin.setPassword(password);
        adminService.register(admin);
        return ResponseEntity.ok("Login successful");
    }
    
}
