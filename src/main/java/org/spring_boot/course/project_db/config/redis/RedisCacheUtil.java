package org.spring_boot.course.project_db.config.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisCacheUtil {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> void cacheList(String key, List<T> list, long ttl, TimeUnit unit) {
        try {
            String json = objectMapper.writeValueAsString(list);
            redisTemplate.opsForValue().set(key, json, ttl, unit);
        } catch (JsonProcessingException e) {
            System.err.println("Ошибка сериализации: " + e.getMessage());
        }
    }

    public <T> List<T> getCachedList(String key, Class<T> clazz) {
        String json = redisTemplate.opsForValue().get(key);
        if (json != null) {
            try {
                return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
            } catch (JsonProcessingException e) {
                System.err.println("Ошибка десериализации: " + e.getMessage());
            }
        }
        return null;
    }

    public void clearCache(String key) {
        redisTemplate.delete(key);
    }
}