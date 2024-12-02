package org.spring_boot.course.project_db.repository.customer;


import org.spring_boot.course.project_db.model.Customer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepository {

    private final NamedParameterJdbcTemplate template;

    public CustomerRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public void save(Customer customer) {
        String sql = "INSERT INTO customers(user_id, full_name) VALUES (:user_id, :full_name)";
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("user_id", customer.getUserId())
                .addValue("full_name", customer.getFullName());
        template.update(sql, map);
    }

    public List<Customer> getAll() {
        String sql = "SELECT * FROM customers";

        return template.query(sql, new CustomerRowMapper());
    }
}
