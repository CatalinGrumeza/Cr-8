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
        registry.addViewController("/faq").setViewName("forward:/faq.html");
        registry.addViewController("/laboratori").setViewName("forward:/laboratori.html");
        registry.addViewController("/dashboard/register").setViewName("forward:/backdoor/register.html");
        registry.addViewController("/dashboard/all-info").setViewName("forward:/backdoor/all-info.html");
        registry.addViewController("/dashboard/all-admin").setViewName("forward:/backdoor/all-admin.html");
        registry.addViewController("/dashboard").setViewName("forward:/backdoor/dashboard.html");
        registry.addViewController("/dashboard/add-labs").setViewName("forward:/backdoor/add-labs.html");
//        registry.addRedirectViewController("/login", "/login.html");

    }
}
