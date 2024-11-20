package org.spring_boot.course.project_db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Balloon {

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
