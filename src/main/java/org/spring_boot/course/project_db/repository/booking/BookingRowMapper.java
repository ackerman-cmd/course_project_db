package org.spring_boot.course.project_db.repository.booking;

import org.spring_boot.course.project_db.model.Booking;
import org.springframework.jdbc.core.RowMapper;

import java.awt.print.Book;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingRowMapper implements RowMapper<Booking> {
    @Override
    public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Booking.createNew(
                rs.getInt("booking_id"),
                rs.getInt("customer_id"),
                rs.getInt("pilot_id"),
                rs.getInt("balloon_id"),
                rs.getInt("route_id"),
                rs.getDate("flight_date").toLocalDate()
        );
    }
}
