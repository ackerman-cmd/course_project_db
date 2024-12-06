package org.spring_boot.course.project_db.controller.customerbooking;

import lombok.RequiredArgsConstructor;
import org.spring_boot.course.project_db.model.CustomerBooking;
import org.spring_boot.course.project_db.service.CustomerBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerBookingController {


    private final CustomerBookingService customerBookingService;

    @GetMapping("/api/customer-bookings")
    public List<CustomerBooking> getCustomerBookings() {
        return customerBookingService.getCustomerBookings();
    }
}

