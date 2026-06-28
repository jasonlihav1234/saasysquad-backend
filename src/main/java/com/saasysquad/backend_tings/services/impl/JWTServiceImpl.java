package com.saasysquad.backend_tings.services.impl;

import com.saasysquad.backend_tings.model.JWTPayload;
import com.saasysquad.backend_tings.model.RefreshToken;
import com.saasysquad.backend_tings.repository.JWTRepository;
import com.saasysquad.backend_tings.services.JWTService;
import io.jsonwebtoken.Claims;
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

    // can make a post construct which makes access key hmacShaKeyFor

    @Override
    public JWTPayload createSessionTokens(UUID userId, String email) {
        String accessToken = createAccessToken(userId, email);
        RefreshToken refreshToken = createRefreshToken(userId, email);
        UUID sessionId = UUID.randomUUID();

        jwtRepository.storeRefreshToken(userId, sessionId, refreshToken.getTokenId());

        return new JWTPayload(accessToken, refreshToken.getRefreshToken(), "Bearer", 600);
    }

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

    public RefreshToken createRefreshToken(UUID userId, String email) {
        UUID tokenId = generateTokenId();
        String refreshToken = Jwts.builder()
                .subject(userId.toString())
                .claim("type", "refresh")
                .id(tokenId.toString())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(Duration.ofDays(7))))
                .issuer("saasysquad-auth")
                .audience().add("saasysquad-api").and()
                .signWith(Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS256)
                .compact();

        return new RefreshToken(tokenId, refreshToken);
    }

    @Override
    public Claims verifyAccessToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public Claims verifyRefreshToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
