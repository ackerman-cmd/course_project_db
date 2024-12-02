package org.spring_boot.course.project_db.service.route;


import lombok.RequiredArgsConstructor;
import org.spring_boot.course.project_db.model.Route;
import org.spring_boot.course.project_db.repository.route.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;

    public Optional<Route> getRoute(int id) {
        return routeRepository.getRoute(id);
    }

    public List<Route> getAll() {
        return routeRepository.getAll();
    }

    public Optional<Route> addRoute(Route route) {
      return  routeRepository.addRoute(route);
    }

    public boolean deleteRoute(int id) {
        return routeRepository.deleteRoute(id);
    }

    public int updateRoute(int id, Route updated) {
        return routeRepository.updateRoute(id, updated);
    }
}
