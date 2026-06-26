package com.saasysquad.backend_tings.services.impl;

import com.saasysquad.backend_tings.model.JWTPayload;
import com.saasysquad.backend_tings.services.JWTService;

public class JWTServiceImpl implements JWTService {
    @Override
    public JWTPayload createSessionTokens(String userId, String email, String device) {
        return null;
    }
}
