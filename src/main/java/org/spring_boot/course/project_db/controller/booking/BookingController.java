package org.spring_boot.course.project_db.controller.booking;


import lombok.RequiredArgsConstructor;
import org.spring_boot.course.project_db.model.Balloon;
import org.spring_boot.course.project_db.model.Booking;
import org.spring_boot.course.project_db.model.Pilot;
import org.spring_boot.course.project_db.model.Route;
import org.spring_boot.course.project_db.repository.customer.CustomerRepository;
import org.spring_boot.course.project_db.service.balloon.BalloonService;
import org.spring_boot.course.project_db.service.booking.BookingService;
import org.spring_boot.course.project_db.service.pilot.PilotService;
import org.spring_boot.course.project_db.service.route.RouteService;
import org.spring_boot.course.project_db.structure.aspects.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    private final RouteService routeService;

    private final PilotService pilotService;

    private final BalloonService balloonService;

    private final CustomerRepository customerRepository;

    @Log
    @PostMapping
    public ResponseEntity<Map<String, String>> createBooking(@RequestBody Booking booking) {
        Map<String, String> response = new HashMap<>();
        try {
            bookingService.createBooking(booking);
            response.put("message", "Бронирование успешно создано!");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalStateException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("error", "Неизвестная ошибка. Попробуйте позже.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    @GetMapping("/pilots")
    public List<Pilot> getPilots() {
        return pilotService.getAll();
    }

    @GetMapping("/routes")
    public List<Route> getRoutes() {
        return routeService.getAll();
    }

    @GetMapping("/balloons")
    public List<Balloon> getBalloons() {
        return balloonService.getAll();
    }
}
