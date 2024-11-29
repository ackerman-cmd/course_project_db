package org.spring_boot.course.project_db.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Table("pilots")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pilot {
    @Id
    private int id;

    private String fullName;

    private String licenseNumber;

    @Override
    public String toString() {
        return "Pilots{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                '}';
    }
}
