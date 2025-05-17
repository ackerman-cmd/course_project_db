package org.spring_boot.course.project_db.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final StringRedisTemplate redisTemplate;

    public void publishBookingCreatedEvent(Long bookingId) {
        String channel = "booking:events";
        String message = "created:" + bookingId;
        redisTemplate.convertAndSend(channel, message);
    }
}
