package com.example.onlineshop.config;

import com.example.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration // Indicates that a class declares one or more @Bean methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime.
@EnableWebSecurity // Enables Spring Security's web security support and provides the Spring MVC integration.
public class SecurityConfig {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // SecurityFilterChain is a type of WebSecurityConfigurerAdapter that allows to customize the security filter chain.
        http
            .authorizeHttpRequests(authorizeRequests -> // Allows restricting access based upon the HttpServletRequest using RequestMatcher implementations.
                    authorizeRequests
                    .requestMatchers("/","/home", "/users/signup", "/login","/verify", "/assign-admin").permitAll() // Allow access to the signup and login pages without authentication
                    .requestMatchers("/currency-converter").hasRole("ADMIN")
                    .requestMatchers("/admin-home").hasRole("ADMIN")
                    .anyRequest().authenticated() // All other requests require authentication
            )
            .formLogin(formLogin ->
                    formLogin
                    .loginPage("/login") // Specify the login page
                    .defaultSuccessUrl("/", true) // Redirect to the home page after successful login
                    .permitAll() // Allow everyone to access the login page
            )
            .logout(logout ->
                logout
                    .logoutUrl("/logout") // Specify the logout URL
                    .logoutSuccessUrl("/login?logout") // Redirect to the login page after successful logout
                    .invalidateHttpSession(true) // Invalidate the session
                    .deleteCookies("JSESSIONID")
                    .permitAll() // Allow everyone to access the logout URL
            );
        return http.build(); // Build the security filter chain
    }

    // Configure the global authentication manager
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoderConfig.passwordEncoder()); // Set up authentication manager with user details service and password encoder

    }
}
