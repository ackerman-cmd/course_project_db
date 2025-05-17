package org.spring_boot.course.project_db.service.booking;

import lombok.RequiredArgsConstructor;
import org.spring_boot.course.project_db.config.redis.EventPublisher;
import org.spring_boot.course.project_db.model.Booking;
import org.spring_boot.course.project_db.repository.booking.BookingRepository;
import org.spring_boot.course.project_db.structure.aspects.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final StringRedisTemplate redisTemplate;
    private final EventPublisher eventPublisher;

    @Log
    @Transactional
    public void createBooking(Booking booking) {
        try {
            bookingRepository.saveBooking(booking);

            // Кэшируем фиктивный статус "оформлено" на 1 день
            String statusKey = "booking:status:" + booking.getId();
            redisTemplate.opsForValue().set(statusKey, "оформлено", 1, TimeUnit.DAYS);

            // Публикуем событие о создании бронирования
            eventPublisher.publishBookingCreatedEvent((long) booking.getId());

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

    // Метод для получения статуса (опционально)
    public String getBookingStatus(Long bookingId) {
        String statusKey = "booking:status:" + bookingId;
        String status = redisTemplate.opsForValue().get(statusKey);
        if (status == null) {
            return "не определен"; // Если статус не найден
        }
        return status;
    }
}