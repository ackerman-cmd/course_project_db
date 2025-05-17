package org.spring_boot.course.project_db.service.pilot;

import lombok.RequiredArgsConstructor;
import org.spring_boot.course.project_db.config.redis.RedisCacheUtil;
import org.spring_boot.course.project_db.model.Pilot;
import org.spring_boot.course.project_db.repository.pilot.PilotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PilotService {

    private final PilotRepository pilotRepository;
    private final RedisCacheUtil redisCacheUtil;
    private static final String PILOT_CACHE_KEY = "pilots:all";

    public Optional<Pilot> getById(int id) {
        return pilotRepository.getPilot(id);
    }

    public List<Pilot> getAll() {
        // Проверяем кэш
        List<Pilot> cachedPilots = redisCacheUtil.getCachedList(PILOT_CACHE_KEY, Pilot.class);
        if (cachedPilots != null) {
            return cachedPilots;
        }

        // Если нет в кэше, получаем из базы
        List<Pilot> pilots = pilotRepository.getAll();
        redisCacheUtil.cacheList(PILOT_CACHE_KEY, pilots, 1, TimeUnit.HOURS); // Кэш на 1 час
        return pilots;
    }

    public Optional<Pilot> addPilot(Pilot pilot) {
        Optional<Pilot> savedPilot = pilotRepository.save(pilot);
        if (savedPilot.isPresent()) {
            redisCacheUtil.clearCache(PILOT_CACHE_KEY); // Очистка кэша при добавлении
        }
        return savedPilot;
    }

    public boolean deletePilote(int id) {
        boolean deleted = pilotRepository.deletePilot(id);
        if (deleted) {
            redisCacheUtil.clearCache(PILOT_CACHE_KEY); // Очистка кэша при удалении
        }
        return deleted;
    }

    public int updatePilot(int id, Pilot updated) {
        int result = pilotRepository.updatePilot(id, updated);
        if (result > 0) {
            redisCacheUtil.clearCache(PILOT_CACHE_KEY); // Очистка кэша при обновлении
        }
        return result;
    }
}