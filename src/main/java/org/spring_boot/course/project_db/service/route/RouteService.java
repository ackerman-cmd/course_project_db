package org.spring_boot.course.project_db.service.route;

import lombok.RequiredArgsConstructor;
import org.spring_boot.course.project_db.config.redis.RedisCacheUtil;
import org.spring_boot.course.project_db.model.Route;
import org.spring_boot.course.project_db.repository.route.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final RedisCacheUtil redisCacheUtil;
    private static final String ROUTE_CACHE_KEY = "routes:all";

    public Optional<Route> getRoute(int id) {
        return routeRepository.getRoute(id);
    }

    public List<Route> getAll() {
        // Проверяем кэш
        List<Route> cachedRoutes = redisCacheUtil.getCachedList(ROUTE_CACHE_KEY, Route.class);
        if (cachedRoutes != null) {
            return cachedRoutes;
        }

        // Если нет в кэше, получаем из базы
        List<Route> routes = routeRepository.getAll();
        redisCacheUtil.cacheList(ROUTE_CACHE_KEY, routes, 1, TimeUnit.HOURS); // Кэш на 1 час
        return routes;
    }

    public Optional<Route> addRoute(Route route) {
        Optional<Route> savedRoute = routeRepository.addRoute(route);
        if (savedRoute.isPresent()) {
            redisCacheUtil.clearCache(ROUTE_CACHE_KEY); // Очистка кэша при добавлении
        }
        return savedRoute;
    }

    public boolean deleteRoute(int id) {
        boolean deleted = routeRepository.deleteRoute(id);
        if (deleted) {
            redisCacheUtil.clearCache(ROUTE_CACHE_KEY); // Очистка кэша при удалении
        }
        return deleted;
    }

    public int updateRoute(int id, Route updated) {
        int result = routeRepository.updateRoute(id, updated);
        if (result > 0) {
            redisCacheUtil.clearCache(ROUTE_CACHE_KEY); // Очистка кэша при обновлении
        }
        return result;
    }
}