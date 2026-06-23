package com.saasysquad.backend_tings.services;

import com.saasysquad.backend_tings.model.User;

public interface AuthService {
    User register(String email, String password, String username);
}
