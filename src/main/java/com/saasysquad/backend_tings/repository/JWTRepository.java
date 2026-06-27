package com.saasysquad.backend_tings.repository;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HexFormat;
import java.util.UUID;

@Repository
public class JWTRepository {

    private final JdbcTemplate jdbcTemplate;

    public JWTRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void storeRefreshToken(UUID userId, UUID sessionId, UUID tokenId) {
        Date expires = Date.from(Instant.now().plus(Duration.ofDays(7)));
        String tokenHash = DigestUtils.sha256Hex(tokenId.toString());

        String sql =
        """
        insert into refresh_tokens (
            token_id,
            user_id,
            token_hash,
            expires,
            revoked,
            device_info,
            created,
            session_id
        )
        values (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        jdbcTemplate.update(sql,
                tokenId,
                userId,
                tokenHash,
                expires,
                false,
                null,
                Date.from(Instant.now()),
                sessionId);

        return;
    }
}
