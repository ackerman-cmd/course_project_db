package org.spring_boot.course.project_db.service;

import lombok.RequiredArgsConstructor;
import org.spring_boot.course.project_db.model.CustomerBooking;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerBookingService {


    private final JdbcTemplate jdbcTemplate;

    public List<CustomerBooking> getCustomerBookings() {
        String sql = "SELECT customer_id, full_name, total_bookings FROM customer_bookings"; // замените на ваше представление
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CustomerBooking(
                rs.getInt("customer_id"),
                rs.getString("full_name"),
                rs.getInt("total_bookings")
        ));
    }
}

