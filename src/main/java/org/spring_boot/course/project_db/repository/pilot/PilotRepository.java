package org.spring_boot.course.project_db.repository.pilot;


import org.spring_boot.course.project_db.model.Pilot;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PilotRepository {
    private final NamedParameterJdbcTemplate template;

    public PilotRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public Optional<Pilot> getPilot(int id) {
        String sql = "SELECT pilot_id, full_name, license_number FROM pilots WHERE pilot_id = :pilot_id";

        try {
            return Optional.of(template.queryForObject(sql, Map.of("pilot_id", id), new PilotRowMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }

    }

    public List<Pilot> getAll() {
        String sql = "SELECT * FROM pilots";

        return template.query(sql, new PilotRowMapper());
    }

    public Optional<Pilot> save(Pilot pilot) {
        String sql = "INSERT INTO pilots (full_name, license_number) VALUES (:full_name, :license_number) RETURNING pilot_id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("full_name", pilot.getFullName())
                .addValue("license_number", pilot.getLicenseNumber());

        // Выполняем вставку и получаем сгенерированный id
        Integer generatedId = template.queryForObject(sql, params, Integer.class);
        if (generatedId != null) {
            pilot.setId(generatedId); // Устанавливаем сгенерированный id в объекте
            return Optional.of(pilot); // Возвращаем объект в Optional
        }

        return Optional.empty(); // Возвращаем пустой Optional, если вставка не удалась
    }




    public int updatePilot(int id, Pilot updated) {
        String sql = "UPDATE pilots SET pilot_id = :pilot_id, full_name = :full_name, license_number = :license_number WHERE pilot_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("pilot_id", updated.getId())
                .addValue("full_name", updated.getFullName())
                .addValue("license_number", updated.getLicenseNumber());

        return template.update(sql, params);
    }

    public boolean deletePilot(int id) {
        String sql = "DELETE FROM pilots WHERE pilot_id = :pilot_id";

        return template.update(sql, Map.of("pilot_id",id)) > 0;
    }


}
