package com.saasysquad.backend_tings.services.impl;

import com.saasysquad.backend_tings.exceptions.InvalidCredentialsException;
import com.saasysquad.backend_tings.model.JWTPayload;
import com.saasysquad.backend_tings.model.User;
import com.saasysquad.backend_tings.repository.UserRepository;
import com.saasysquad.backend_tings.services.JWTService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.saasysquad.backend_tings.services.AuthService;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTService jwtService;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, JWTService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
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
    public JWTPayload login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
        return jwtService.createSessionTokens(user.getId(), user.getEmail());
    }
}
