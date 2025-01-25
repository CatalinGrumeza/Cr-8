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

/**
 * The SecurityConfig class is a Spring configuration class 
 * that defines security settings for the application. 
 * It configures authentication, authorization, password encoding, 
 * session management, and custom success handling for logins.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AdminService adminService;

    // Constructor-based injection with @Lazy to prevent circular dependency
    public SecurityConfig(@Lazy AdminService adminService) {
        this.adminService = adminService;
    }
    
    /**
     * Defines a PasswordEncoder bean to encrypt passwords using 
     * the BCrypt hashing function. This enhances security by 
     * preventing plain-text password storage.
     *
     * @return a PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Encrypts passwords securely
    }

    /**
     * Provides an AuthenticationManager bean for handling 
     * authentication processes. This is required for Spring Security 
     * to manage authentication strategies.
     *
     * @param authenticationConfiguration the configuration for authentication.
     * @return an AuthenticationManager instance.
     * @throws Exception if any issues occur during authentication setup.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configures the SecurityFilterChain that dictates how 
     * requests are handled with respect to security. It sets 
     * up CSRF protection, request authorization rules, 
     * login form configuration, and session management settings.
     *
     * @param http the HttpSecurity object used to configure web-based security.
     * @return a SecurityFilterChain that applies the defined security settings.
     * @throws Exception if any issues occur during security configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    // Here we can set up rules for which requests can be accessed without authentication
                    auth.anyRequest().permitAll(); // Allow all requests (for example purposes)
                })
                .formLogin(form -> form
                    .loginPage("/login") // Custom login page URL
                    .defaultSuccessUrl("/", true) // Redirect after successful login
                    .loginProcessingUrl("/login") // URL to submit login requests
                    .usernameParameter("email") // Username field for login
                    .passwordParameter("password") // Password field for login
                    .successHandler(customAuthenticationSuccessHandler()) // Custom handler for successful logins
                    .failureHandler((request, response, exception) -> {
                        response.setStatus(401); // Sets unauthorized status on failure
                        response.setContentType("application/json"); // JSON response type
                        response.setCharacterEncoding("UTF-8"); // Character encoding
                    })
                    .permitAll() // Allow all users to access the login page
                )
                .logout(logout -> logout
                    .logoutSuccessUrl("/?logout") // Redirect after successful logout
                    .invalidateHttpSession(true) // Invalidate session on logout
                    .deleteCookies("JSESSIONID") // Delete session cookies
                    .permitAll() // Allow all users to access logout
                )
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Create sessions as necessary
                    .maximumSessions(1) // Limit to a single active session per user
                    .expiredUrl("/login?expired") // Redirect to login on session expiration
                )
                .build(); // Build the security filter chain
    }

    /**
     * Creates a custom AuthenticationSuccessHandler to handle 
     * login success events. It retrieves the authenticated user's 
     * details and sends a JSON response containing the user's name 
     * and role.
     *
     * @return an AuthenticationSuccessHandler for handling successful logins.
     */
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            System.out.println("User's name is: " + authentication.getName());
            
            // Get the authenticated Admin entity from the service layer
            Admin admin = adminService.getAdminByEmail(authentication.getName());
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("name", admin.getName()); // Add admin's name to response
            responseBody.put("role", admin.getRole().getName()); // Add admin's role to response

            response.setContentType("application/json"); // Set response type to JSON
            response.setStatus(HttpServletResponse.SC_OK); // Set HTTP status to OK
            ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper to convert objects to JSON
            response.getWriter().write(objectMapper.writeValueAsString(responseBody)); // Write the response body as JSON
            response.getWriter().flush(); // Ensure the response is sent immediately
        };
    }

    /**
     * Provides a ScheduledExecutorService bean with a fixed thread pool 
     * of size 5. This can be utilized for executing asynchronous tasks 
     * on scheduled intervals.
     *
     * @return a ScheduledExecutorService instance.
     */
    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(5); // Pool with 5 threads for scheduled tasks
    }
}