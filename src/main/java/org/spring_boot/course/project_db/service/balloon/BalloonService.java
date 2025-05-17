package org.spring_boot.course.project_db.service.balloon;

import lombok.RequiredArgsConstructor;
import org.spring_boot.course.project_db.config.redis.RedisCacheUtil;
import org.spring_boot.course.project_db.model.Balloon;
import org.spring_boot.course.project_db.repository.balloon.BalloonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BalloonService {

    private final BalloonRepository balloonRepository;
    private final RedisCacheUtil redisCacheUtil;
    private static final String BALLOON_CACHE_KEY = "balloons:all";

    public Optional<Balloon> getById(int id) {
        return balloonRepository.getById(id);
    }

    public List<Balloon> getAll() {
        // Проверяем кэш
        List<Balloon> cachedBalloons = redisCacheUtil.getCachedList(BALLOON_CACHE_KEY, Balloon.class);
        if (cachedBalloons != null) {
            return cachedBalloons;
        }

        // Если нет в кэше, получаем из базы
        List<Balloon> balloons = balloonRepository.getAll();
        redisCacheUtil.cacheList(BALLOON_CACHE_KEY, balloons, 1, TimeUnit.HOURS); // Кэш на 1 час
        return balloons;
    }

    public int deleteBalloon(int id) {
        int result = balloonRepository.deleteById(id);
        if (result > 0) {
            redisCacheUtil.clearCache(BALLOON_CACHE_KEY); // Очистка кэша при удалении
        }
        return result;
    }

    public int update(int id, Balloon updated) {
        int result = balloonRepository.updateBalloon(id, updated);
        if (result > 0) {
            redisCacheUtil.clearCache(BALLOON_CACHE_KEY); // Очистка кэша при обновлении
        }
        return result;
    }

    public Optional<Balloon> addBalloon(Balloon balloon) {
        Optional<Balloon> savedBalloon = balloonRepository.addBalloon(balloon);
        if (savedBalloon.isPresent()) {
            redisCacheUtil.clearCache(BALLOON_CACHE_KEY); // Очистка кэша при добавлении
        }
        return savedBalloon;
    }
}