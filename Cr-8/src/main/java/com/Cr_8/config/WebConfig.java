package com.Cr_8.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer{
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/swagger", "/swagger-ui.html");
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/login").setViewName("forward:/login.html");
        registry.addViewController("/dashboard/register").setViewName("forward:/backoffice/register.html");
        registry.addViewController("/dashboard/all-info").setViewName("forward:/backoffice/all-info.html");
        registry.addViewController("/super/all-admins").setViewName("forward:/backoffice/all-admins.html");
        
        registry.addViewController("/forgot-password").setViewName("forward:/forgot-password.html");
        registry.addViewController("/code-verification").setViewName("forward:/verification-code.html");
        registry.addViewController("/newpassword").setViewName("forward:/newpassowrd.html");
        
        registry.addViewController("/dashboard/all-bookings").setViewName("forward:/backoffice/all-bookings.html");
        registry.addViewController("/dashboard").setViewName("forward:/backoffice/dashboard.html");
//        registry.addRedirectViewController("/login", "/login.html");
       // registry.addViewController("/dashboard/delete-labs").setViewName("forward:/backoffice/delete-labs.html");
       // registry.addViewController("/dashboard/add-labs").setViewName("forward:/backoffice/add-labs.html");
        

    }
}
