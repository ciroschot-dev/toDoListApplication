package com.proyecto.todolistapplication.service;

import com.proyecto.todolistapplication.model.User;
import com.proyecto.todolistapplication.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserService
{
    private final UsersRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;


    public User registerUser(User user)
    {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public String authenticateUser(User user)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated())
        {
            return jwtService.generateToken(user.getUsername());
        }

        return "Invalid username or password";
    }
}
