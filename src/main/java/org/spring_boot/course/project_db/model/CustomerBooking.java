package org.spring_boot.course.project_db.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;

@Data
@Table("customer_booking")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CustomerBooking {
    @Id
    private int customer_id;

    private String full_name;

    private Integer total_bookings;
}
