package org.spring_boot.course.project_db.repository.route;

import org.spring_boot.course.project_db.model.Route;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RouteRowMapper implements RowMapper<Route> {

    @Override
    public Route mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Route(
                rs.getInt("route_id"),
                rs.getString("start_location"),
                rs.getString("end_location")
        );
    }
}
