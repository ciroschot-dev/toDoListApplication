package com.proyecto.todolistapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

// Marks this class as a Spring configuration class.
@Configuration
// Enables Spring Security web support.
@EnableWebSecurity
public class SecurityConfig
{

    // Registers the main security filter chain for HTTP requests.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
                // In stateless REST APIs without forms, CSRF is usually disabled.
                .csrf(customizer -> customizer.disable())
                // Every request requires an authenticated user.
                .authorizeHttpRequests(requests -> requests.anyRequest().authenticated())
                // Uses HTTP Basic authentication (username/password on each request).
                .httpBasic(Customizer.withDefaults())
                // Does not keep server-side session state; each request must be authenticated.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
