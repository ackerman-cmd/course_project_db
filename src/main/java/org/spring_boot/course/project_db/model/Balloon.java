package org.spring_boot.course.project_db.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Table("balloons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Balloon {
    @Id
    private int id;

    private String model;

    private int capacity;

    private String status;

    @Override
    public String toString() {
        return "Balloons{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", capacity=" + capacity +
                ", status='" + status + '\'' +
                '}';
    }
}
