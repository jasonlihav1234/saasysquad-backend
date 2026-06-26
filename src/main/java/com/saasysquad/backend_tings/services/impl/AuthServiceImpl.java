package com.saasysquad.backend_tings.services.impl;

import com.saasysquad.backend_tings.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.saasysquad.backend_tings.services.AuthService;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String email, String password, String username) {
        String hashedPassword = passwordEncoder.encode(password);
        User newUser = new User();

        newUser.setId(UUID.randomUUID());
        newUser.setEmail(email);
        newUser.setPasswordHash(hashedPassword);
        newUser.setUsername(username);

        return newUser;
    }

    @Override
    public User login(String email, String password) {
        return null;
    }
}
