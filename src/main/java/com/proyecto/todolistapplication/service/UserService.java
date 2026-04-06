package com.proyecto.todolistapplication.service;

import com.proyecto.todolistapplication.model.MyUserDetails;
import com.proyecto.todolistapplication.model.User;
import com.proyecto.todolistapplication.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@NullMarked

public class UserService implements UserDetailsService
{
    private final UsersRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = repo.findByUsername(username);

        if (user == null)
        {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new MyUserDetails(user);
    }
}
