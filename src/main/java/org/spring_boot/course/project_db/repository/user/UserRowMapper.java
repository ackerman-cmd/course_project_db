package org.spring_boot.course.project_db.repository.user;

import org.spring_boot.course.project_db.model.Role;
import org.spring_boot.course.project_db.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .userId(rs.getInt("user_id"))
                .userName(rs.getString("username"))
                .passwordHash(rs.getString("password_hash"))
                .role(Role.valueOf(rs.getString("role").toUpperCase()))
                .build();

    }
}
