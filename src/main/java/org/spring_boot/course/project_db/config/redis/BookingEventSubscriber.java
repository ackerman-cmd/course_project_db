package org.spring_boot.course.project_db.config.redis;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookingEventSubscriber implements MessageListener {

    private final StringRedisTemplate redisTemplate;

    @PostConstruct
    public void subscribe() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisTemplate.getConnectionFactory());
        container.addMessageListener(this, new ChannelTopic("booking:events"));
        container.afterPropertiesSet();
        container.start();
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        String[] parts = msg.split(":");
        if (parts.length == 2 && "created".equals(parts[0])) {
            Long bookingId = Long.parseLong(parts[1]);
            log.info("Новое бронирование создано! Booking ID: {}", bookingId);
            // Здесь можно отправить уведомление, например, через WebSocket, email
        }
    }
}