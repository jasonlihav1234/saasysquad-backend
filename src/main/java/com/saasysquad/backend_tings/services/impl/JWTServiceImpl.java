package com.saasysquad.backend_tings.services.impl;

import com.saasysquad.backend_tings.model.JWTPayload;
import com.saasysquad.backend_tings.repository.JWTRepository;
import com.saasysquad.backend_tings.services.JWTService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JWTServiceImpl implements JWTService {
    private final JWTRepository jwtRepository;

    @Value("${jwt.secret}")
    private String accessSecret;

    @Value("${refresh.secret}")
    private String refreshSecret;

    public JWTServiceImpl(JWTRepository jwtRepository) {
        this.jwtRepository = jwtRepository;
    }

    public UUID generateTokenId() {
        return UUID.randomUUID();
    }

    @Override
    public JWTPayload createSessionTokens(UUID userId, String email) {
        return null;
    }

    @Override
    public String createAccessToken(UUID userId, String email) {
        UUID tokenId = generateTokenId();

        return Jwts.builder()
                .subject(userId.toString())
                .claim("email", email)
                .claim("type", "access")
                .id(tokenId.toString())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(Duration.ofMinutes((15)))))
                .issuer("saasysquad-auth")
                .audience().add("saasysquad-api").and()
                .signWith(Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS256)
                .compact();
    }
}
