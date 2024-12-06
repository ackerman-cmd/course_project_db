package org.spring_boot.course.project_db.repository.booking;

import org.spring_boot.course.project_db.model.Booking;
import org.spring_boot.course.project_db.structure.aspects.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookingRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookingRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Log
    public void saveBooking(Booking booking) {
        String sql = "INSERT INTO bookings (customer_id, pilot_id, balloon_id, route_id, flight_date) VALUES (:customer_id, :pilot_id, :balloon_id, :route_id, :flight_date)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("customer_id", booking.getCustomerId())
                .addValue("pilot_id", booking.getPilotId())
                .addValue("balloon_id", booking.getBalloonId())
                .addValue("route_id", booking.getRouteId())
                .addValue("flight_date", booking.getFlightDate());

        jdbcTemplate.update(sql, params);
    }


}
