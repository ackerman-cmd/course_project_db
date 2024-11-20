package org.spring_boot.course.project_db.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pilot {

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
