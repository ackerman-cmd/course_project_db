package org.spring_boot.course.project_db.service.booking;

import lombok.RequiredArgsConstructor;
import org.spring_boot.course.project_db.model.Booking;
import org.spring_boot.course.project_db.repository.booking.BookingRepository;
import org.spring_boot.course.project_db.structure.aspects.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    @Log
    @Transactional
    public void createBooking(Booking booking) {
        try {
            bookingRepository.saveBooking(booking);
        } catch (DataAccessException e) {
            if (e.getMessage() != null && e.getMessage().contains("Balloon is not available for the selected date.")) {
                throw new IllegalStateException("Шар недоступен для выбранной даты.");
            } else if (e.getMessage() != null && e.getMessage().contains("Pilot is not available for the selected date.")) {
                throw new IllegalStateException("Пилот недоступен для выбранной даты.");
            } else {
                throw new IllegalStateException("Ошибка при создании бронирования.", e);
            }
        }
    }

}
