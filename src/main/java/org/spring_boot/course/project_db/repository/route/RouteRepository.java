package org.spring_boot.course.project_db.repository.route;


import lombok.RequiredArgsConstructor;
import org.spring_boot.course.project_db.model.Route;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RouteRepository {

    private final NamedParameterJdbcTemplate template;

    public Optional<Route> getRoute(int id) {
        String sql = "SELECT route_id, start_location, end_location FROM routes WHERE route_id = :route_id";

        try {
            return Optional.of(template.queryForObject(sql, Map.of("route_id", id), new RouteRowMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }

    }

    public List<Route> getAll() {
        String sql = "SELECT * FROM routes ORDER BY route_id";

        return template.query(sql, new RouteRowMapper());
    }

    public Optional<Route> addRoute(Route route) {
        String sql = "INSERT INTO routes (start_location, end_location) VALUES (:start_location, :end_location) RETURNING route_id";

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("start_location", route.getStartLocation())
                .addValue("end_location", route.getEndLocation());

        // Выполняем вставку с возвращением сгенерированного ID
        Integer generatedId = template.queryForObject(sql, map, Integer.class);

        if (generatedId != null) {
            // Устанавливаем ID в объект Route
            route.setId(generatedId);
            return Optional.of(route);
        } else {
            return Optional.empty();
        }
    }



    public boolean deleteRoute(int id) {
        String sql = "DELETE FROM routes WHERE route_id = :route_id";

        return template.update(sql, Map.of("route_id", id)) > 0;
    }

    public int updateRoute(int id, Route updated) {
        String sql = "UPDATE routes SET start_location = :start_location, end_location = :end_location WHERE route_id = :route_id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("start_location", updated.getStartLocation())
                .addValue("end_location", updated.getEndLocation());

        return template.update(sql, parameterSource);
    }
}
