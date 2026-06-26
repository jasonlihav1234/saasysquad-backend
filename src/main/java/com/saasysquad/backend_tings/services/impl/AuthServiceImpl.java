package com.saasysquad.backend_tings.services.impl;

import com.saasysquad.backend_tings.model.User;
import com.saasysquad.backend_tings.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.saasysquad.backend_tings.services.AuthService;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public User register(String email, String password, String username) {
        String hashedPassword = passwordEncoder.encode(password);
        User newUser = new User();

        newUser.setId(UUID.randomUUID());
        newUser.setEmail(email);
        newUser.setPasswordHash(hashedPassword);
        newUser.setUsername(username);

        return userRepository.create(newUser);
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new);

        return null;
    }
}
