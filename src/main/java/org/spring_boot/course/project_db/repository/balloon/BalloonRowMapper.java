package org.spring_boot.course.project_db.repository.balloon;

import org.spring_boot.course.project_db.model.Balloon;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BalloonRowMapper implements RowMapper<Balloon> {

    @Override
    public Balloon mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Balloon(
                rs.getInt("balloon_id"),
                rs.getString("model"),
                rs.getInt("capacity"),
                rs.getString("status")
        );
    }
}
