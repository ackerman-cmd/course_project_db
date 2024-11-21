package org.spring_boot.course.project_db.repository;

import org.spring_boot.course.project_db.model.User;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);


    public UserDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
    }

    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.getUserId() == 0) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setUserId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update("" +
                    "UPDATE users SET username = :userName, password_hash = :passwordHash, role = :role WHERE user_id = :userId", parameterSource);
        }
        return user;
    }

    public User getByName(String name) {
        List<User> userWithName =  jdbcTemplate.query("SELECT * FROM users WHERE username = ?", rowMapper, name);
        return DataAccessUtils.singleResult(userWithName);
    }

    public User getById(int id) {
        return DataAccessUtils.singleResult(jdbcTemplate.query("select * from users where user_id = :user_id ", rowMapper, id));
    }

    public boolean deleteById(int id) {
        return jdbcTemplate.update("DELETE from users WHERE user_id = ?", rowMapper, id) != 0;
    }

    public List<User> allUsersWithTheSamePassword(String rawPassword) {
        return jdbcTemplate.query("select * from users where password_hash = ?", rowMapper, rawPassword);
    }
}
