package com.saasysquad.backend_tings.repository;

import com.saasysquad.backend_tings.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User create(User user) {
        String sql = "insert into users " +
                    "(user_id, user_name, email, password_hash, created_at) " +
                    "values " +
                    "(?, ?, ? ,? ,?)";

        jdbcTemplate.update(sql,
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getPasswordHash(),
                            Instant.now().toString());

        return user;
    }
}
