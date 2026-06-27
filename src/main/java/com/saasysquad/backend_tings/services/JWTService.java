package com.saasysquad.backend_tings.services;

import com.saasysquad.backend_tings.model.JWTPayload;

import java.util.UUID;

public interface JWTService {
    public JWTPayload createSessionTokens(UUID userId, String email);
    public String createAccessToken(UUID userId, String email);
//    public String createRefreshToken()
}
