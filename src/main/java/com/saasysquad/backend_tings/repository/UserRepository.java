package com.saasysquad.backend_tings.repository;

import com.saasysquad.backend_tings.exceptions.UserAlreadyExistsException;
import com.saasysquad.backend_tings.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        try {
            jdbcTemplate.update(sql,
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPasswordHash(),
                    Timestamp.valueOf(LocalDateTime.now()));
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException("User already exists " + user.getEmail(), e);
        }

        return user;
    }

    public Optional<User> findByEmail(String email) {
        String sql = "select * from users where email = ?";

        List<User> users = jdbcTemplate.query(sql, (row, rowNum) -> {
            User user = new User();
            user.setId(row.getObject("user_id", java.util.UUID.class));
            user.setEmail(row.getString("email"));
            user.setUsername(row.getString("user_name"));
            user.setPasswordHash((row.getString("password_hash")));
            return user;
        }, email);

        return users.stream().findFirst();
    }
}
