package com.proyecto.todolistapplication.service;

import com.proyecto.todolistapplication.model.User;
import com.proyecto.todolistapplication.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserService
{
    private final UsersRepository repo;
    private final PasswordEncoder encoder;

    public User registerUser(User user)
    {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }
}
