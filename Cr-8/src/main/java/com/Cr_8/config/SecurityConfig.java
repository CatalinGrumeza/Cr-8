//package com.Cr_8.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.Cr_8.servicies.AdminDetailsService;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//	@Autowired
//	private AdminDetailsService adminDetailsService;
//	@Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();  // Pass encoder
//    }
//
//}
