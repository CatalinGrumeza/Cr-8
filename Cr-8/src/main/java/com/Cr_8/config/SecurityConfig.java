package com.Cr_8.config;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

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
//                    auth.requestMatchers("/login", "/login.html", "/index.html", "/register.html", "/", "/style/**", "/script/**","/image/**","/api/pub/**").permitAll();
//                	  auth.requestMatchers("/api/**").hasAnyRole("ADMIN","SUPER_ADMIN");
//                	  auth.requestMatchers("/api/super/**").hasRole("SUPER_ADMIN");
//                    auth.anyRequest().authenticated();
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
        	
            response.setStatus(200); // OK status
            response.getWriter().write("Login successful");
            response.sendRedirect("/"); // Redirect to the landing page
        };
    }
    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(5); // Pool con 5 thread
    }
}