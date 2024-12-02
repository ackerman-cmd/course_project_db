package org.spring_boot.course.project_db.controller.route;

import org.spring_boot.course.project_db.model.Route;
import org.spring_boot.course.project_db.service.route.RouteService;
import org.spring_boot.course.project_db.structure.aspects.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log
@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public List<Route> getAll() {
        return routeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> getById(@PathVariable int id) {
        Optional<Route> route = routeService.getRoute(id);

        return route.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<Route> addRoute(@RequestBody Route route) {
        Optional<Route> saved =  routeService.addRoute(route);
        return saved.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRoute(@PathVariable int id, @RequestBody Route route) {
        route.setId(id);
        routeService.updateRoute(id, route);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable int id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }
}
