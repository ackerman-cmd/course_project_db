package org.spring_boot.course.project_db.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;


@Data
@Table("bookings")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    @Id
    private int id;

    private int customerId;

    private int pilotId;

    private int balloonId;

    private int routeId;

    private LocalDate flightDate;

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", pilotId=" + pilotId +
                ", balloonId=" + balloonId +
                ", routeId=" + routeId +
                ", flightDate=" + flightDate +
                '}';
    }

    public static Booking createNew(int booking_id, int customerId, int pilotId, int balloonId, int routeId, LocalDate flightDate) {
        return Booking.builder()
                .id(booking_id)
                .customerId(customerId)
                .pilotId(pilotId)
                .balloonId(balloonId)
                .routeId(routeId)
                .flightDate(flightDate)
                .build();
    }
}
