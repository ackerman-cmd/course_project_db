package org.spring_boot.course.project_db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    private int id;

    private int customerId;

    private int pilotId;

    private int balloonId;

    private int routeId;

    private LocalDate flightDate;

    private String status;

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", pilotId=" + pilotId +
                ", balloonId=" + balloonId +
                ", routeId=" + routeId +
                ", flightDate=" + flightDate +
                ", status='" + status + '\'' +
                '}';
    }
}
