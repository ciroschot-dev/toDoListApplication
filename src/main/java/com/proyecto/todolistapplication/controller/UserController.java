package com.proyecto.todolistapplication.controller;

import com.proyecto.todolistapplication.model.User;
import com.proyecto.todolistapplication.service.MyUserDetailsService;
import com.proyecto.todolistapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo-app")
@RequiredArgsConstructor

public class UserController
{
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user)
    {
        return ResponseEntity.ok(userService.registerUser(user));
    }
}
