package com.example.onlineshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@Configuration // Indicates that a class declares one or more @Bean methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime.
@EnableWebSecurity // Enables Spring Security's web security support and provides the Spring MVC integration.
public class SecurityConfig {

}
