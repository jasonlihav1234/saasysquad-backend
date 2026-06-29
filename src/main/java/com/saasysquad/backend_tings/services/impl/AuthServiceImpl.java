package com.saasysquad.backend_tings.services.impl;

import com.saasysquad.backend_tings.exceptions.InvalidCredentialsException;
import com.saasysquad.backend_tings.exceptions.InvalidPasswordException;
import com.saasysquad.backend_tings.exceptions.ResetPasswordTokenInvalidException;
import com.saasysquad.backend_tings.exceptions.UserNotFoundException;
import com.saasysquad.backend_tings.model.JWTPayload;
import com.saasysquad.backend_tings.model.User;
import com.saasysquad.backend_tings.repository.UserRepository;
import com.saasysquad.backend_tings.services.JWTService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.saasysquad.backend_tings.services.AuthService;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final StringRedisTemplate redisTemplate;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, JWTService jwtService, StringRedisTemplate redisTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.redisTemplate = redisTemplate;
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

    public User checkUser(String email, String password) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) return null;

        User user = optionalUser.get();
        boolean checkPassword = passwordEncoder.matches(user.getPasswordHash(), password);

        if (!checkPassword) return null;

        return user;
    }

    public String resetPassword(String email, String token, String password) {
        if (password.length() < 7) {
            throw new InvalidPasswordException("Password is too short");
        }

        String resetPasswordToken = redisTemplate.opsForValue().get("resetPassword:" + email);

        if (resetPasswordToken == null || !resetPasswordToken.equals(token)) {
            throw new ResetPasswordTokenInvalidException("Password token not found");
        }

        String passwordHash = passwordEncoder.encode(password);
        userRepository.updatePasswordByEmail(email, passwordHash);

        redisTemplate.delete("resetPassword:" + email);

        return "Password successfully updated";
    }

}
