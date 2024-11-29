package org.spring_boot.course.project_db.repository.user;

import lombok.RequiredArgsConstructor;
import org.spring_boot.course.project_db.model.Role;
import org.spring_boot.course.project_db.model.User;
import org.spring_boot.course.project_db.structure.aspects.Log;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Optional<User> findById(int userId) {
        final String sql = """
                SELECT user_id, username, password_hash, role
                FROM users
                WHERE user_id = :userId
                """;

        try {
            User user = jdbcTemplate.queryForObject(sql, Map.of("userId", userId), new UserRowMapper());
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByUsername(String username) {
        final String sql = """
                SELECT user_id, username, password_hash, role
                FROM users
                WHERE username = :username
                """;

        try {
            User user = jdbcTemplate.queryForObject(sql, Map.of("username", username), new UserRowMapper());
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Log
    public void save(User user) {
        final String sql = "INSERT INTO users (username, password_hash, role) VALUES (:username, :passwordHash, :role)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("passwordHash", user.getPasswordHash())
                .addValue("role", user.getRole().toString().toLowerCase());

        jdbcTemplate.update(sql, params);
    }

    public int updateUser(int userId, User updatedUser) {
        final String sql = """
                UPDATE users
                SET username = :username, password_hash = :passwordHash, role = :role
                WHERE user_id = :userId
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", updatedUser.getUsername())
                .addValue("passwordHash", updatedUser.getPasswordHash())
                .addValue("role", updatedUser.getRole().toString().toLowerCase())
                .addValue("userId", userId);

        return jdbcTemplate.update(sql, params);
    }

    public List<User> findAll(String role) {
        final String sql = (role != null) ? """
                SELECT user_id, username, password_hash, role
                FROM users
                WHERE role = :role
                """ : """
                SELECT user_id, username, password_hash, role
                FROM users
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        if (role != null) {
            params.addValue("role", role);
        }

        return jdbcTemplate.query(sql, params, new UserRowMapper());
    }
}
