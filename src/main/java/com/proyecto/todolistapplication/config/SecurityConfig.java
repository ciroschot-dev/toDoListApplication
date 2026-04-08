package com.proyecto.todolistapplication.config;

import com.proyecto.todolistapplication.service.JWTService;
import com.proyecto.todolistapplication.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Marks this class as a Spring configuration class.
@Configuration
// Enables Spring Security web support.
@EnableWebSecurity

public class SecurityConfig
{
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(12);
    }

    // Registers the main security filter chain for HTTP requests.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JWTService jwtService, MyUserDetailsService userDetailsService)
    {
        return http
                // In stateless REST APIs without forms, CSRF is usually disabled.
                .csrf(AbstractHttpConfigurer::disable)
                // Every request requires an authenticated user, except in the register.
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/todo-app/register", "/todo-app/login").permitAll()
                        .anyRequest().authenticated())
                // Uses HTTP Basic authentication (username/password on each request).
                .httpBasic(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults())
                // Does not keep server-side session state; each request must be authenticated.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtFilter(jwtService, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
    {
        return config.getAuthenticationManager();
    }
}