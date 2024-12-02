package org.spring_boot.course.project_db.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Table("customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    private int id;

    private int userId;

    private String fullName;

    public static Customer createNew(Integer id, String fullName) {
        return Customer.builder()
                .userId(id)
                .fullName(fullName)
                .build();
    }

    @Override
    public String toString() {
        return "Customers{" +
                "id=" + id +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
