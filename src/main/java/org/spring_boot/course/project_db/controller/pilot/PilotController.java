package org.spring_boot.course.project_db.controller.pilot;


import org.spring_boot.course.project_db.model.Balloon;
import org.spring_boot.course.project_db.model.Pilot;
import org.spring_boot.course.project_db.model.Route;
import org.spring_boot.course.project_db.service.pilot.PilotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pilots")
public class PilotController {

    private final PilotService pilotService;

    public PilotController(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    @GetMapping
    public List<Pilot> getAll() {
        return pilotService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pilot> getById(@PathVariable int id) {
        Optional<Pilot> pilot = pilotService.getById(id);

        return pilot.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<Pilot> addPilot(@RequestBody Pilot pilot) {
        Optional<Pilot> saved =  pilotService.addPilot(pilot);
        return saved.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePilot(@PathVariable int id, @RequestBody Pilot pilot) {
        pilot.setId(id);
        pilotService.updatePilot(id, pilot);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePilot(@PathVariable int id) {
        pilotService.deletePilote(id);
        return ResponseEntity.noContent().build();
    }
}
