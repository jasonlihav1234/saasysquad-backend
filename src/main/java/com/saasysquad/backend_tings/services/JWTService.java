package com.saasysquad.backend_tings.services;

import com.saasysquad.backend_tings.model.JWTPayload;

public interface JWTService {
    public JWTPayload createSessionTokens(String userId, String email, String device);
}
