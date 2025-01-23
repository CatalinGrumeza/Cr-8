package com.Cr_8.config;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.Cr_8.entities.Admin;
import com.Cr_8.servicies.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
	private final AdminService adminService;
	
	public SecurityConfig(@Lazy AdminService adminService) { // Lazy used to bypass circular reference between adminservice and securityconfig
        this.adminService = adminService;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Encrypts passwords securely
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
//                   auth.requestMatchers("/login", "/login.html", "/index.html", "/register.html", "/","/styles/**", "/scripts/**","/image/**","/api/pub/**","/assets/**").permitAll();
//                	  auth.requestMatchers("/api/**","/backoffice/**","/dashboard/**","/dashboard","/static/**").hasAnyRole("ADMIN","SUPER_ADMIN");
//                	  auth.requestMatchers("/api/super/**","/backoffice/dashboard.html","/static/**","/dashboard/all-admins","/super/**").hasRole("SUPER_ADMIN");
//                   auth.anyRequest().authenticated();
                auth.anyRequest().permitAll();
                })
                .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/", true)
                    .loginProcessingUrl("/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(customAuthenticationSuccessHandler())
                    .failureHandler((request, response, exception) -> {
                        response.setStatus(401);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                    })
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutSuccessUrl("/?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                )
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Allows creating sessions when needed
                    .maximumSessions(1) // Limits the number of active sessions per user
                    .expiredUrl("/login?expired") // Redirect when session expired
                )
                .build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
        	System.out.println("il nome è:"+authentication.getName());
        	
        	Admin admin = adminService.getAdminByEmail(authentication.getName());
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("name", admin.getName());
            responseBody.put("role", admin.getRole().getName());

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));
            response.getWriter().flush();
            response.sendRedirect("/dashboard/all-info"); // Redirect to the landing page
        };
    }
    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(5); // Pool con 5 thread
    }
}