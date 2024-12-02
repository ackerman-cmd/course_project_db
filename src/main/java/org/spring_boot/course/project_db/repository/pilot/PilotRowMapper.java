package org.spring_boot.course.project_db.repository.pilot;

import org.spring_boot.course.project_db.model.Pilot;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PilotRowMapper implements RowMapper<Pilot> {
    @Override
    public Pilot mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Pilot(
                rs.getInt("pilot_id"),
                rs.getString("full_name"),
                rs.getString("license_number")
        );
    }
}
