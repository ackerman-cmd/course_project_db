package org.spring_boot.course.project_db.repository.balloon;

import org.spring_boot.course.project_db.model.Balloon;
import org.spring_boot.course.project_db.structure.aspects.Log;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Log
public class BalloonRepository {
    private final NamedParameterJdbcTemplate template;

    public BalloonRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public Optional<Balloon> getById(int id) {
        final String sql = "SELECT balloon_id, model, capacity, status from balloons WHERE balloon_id = :balloon_id";
        return Optional.of(template.queryForObject(sql, Map.of("balloon_id", id), new BalloonRowMapper()));
    }

    public List<Balloon> getAll() {
        final String sql = "SELECT balloon_id,model, capacity, status FROM balloons";

        return template.query(sql, new BalloonRowMapper());

    }

    public int deleteById(int id) {
        final String sql = "DELETE FROM balloons WHERE balloon_id = :balloon_id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("balloon_id", id);

        return template.update(sql, params);
    }

    public int updateBalloon(int id, Balloon updated) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("model", updated.getModel())
                .addValue("capacity", updated.getCapacity())
                .addValue("status", updated.getStatus());

        final String sql = "UPDATE balloons SET model = :model, capacity = :capacity, status = :status WHERE balloon_id = :id";

        return template.update(sql, params);
    }

    public void addBalloon(Balloon balloon) {
        final String sql = "INSERT INTO balloons(balloon_id, model, capacity, status)" +
                " VALUES (:balloon_id, :model, :capacity, :status)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("balloon_id", balloon.getId())
                .addValue("model", balloon.getModel())
                .addValue("capacity", balloon.getCapacity())
                .addValue("status", balloon.getStatus());

        template.update(sql, parameterSource);
    }
}
