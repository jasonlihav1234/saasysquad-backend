package com.saasysquad.backend_tings.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.saasysquad.backend_tings.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(String email, String password, String username) {
        String hashedPassword = passwordEncoder.encode(password);
    }
}
