package com.saasysquad.backend_tings.services.impl;

import com.saasysquad.backend_tings.model.JWTPayload;
import com.saasysquad.backend_tings.repository.JWTRepository;
import com.saasysquad.backend_tings.services.JWTService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JWTServiceImpl implements JWTService {
    private final JWTRepository jwtRepository;

    public JWTServiceImpl(JWTRepository jwtRepository) {
        this.jwtRepository = jwtRepository;
    }

    @Override
    public JWTPayload createSessionTokens(UUID userId, String email) {
        return null;
    }
}
