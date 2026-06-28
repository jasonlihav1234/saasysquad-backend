package com.saasysquad.backend_tings.services;

import com.saasysquad.backend_tings.model.JWTPayload;
import io.jsonwebtoken.Claims;

import java.util.UUID;

public interface JWTService {
    public JWTPayload createSessionTokens(UUID userId, String email);
    public Claims verifyAccessToken(String token);
    public Claims verifyRefreshToken(String token);
}
