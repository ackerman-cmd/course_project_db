package org.spring_boot.course.project_db.controller.balloon;

import org.spring_boot.course.project_db.model.Balloon;
import org.spring_boot.course.project_db.service.balloon.BalloonService;
import org.spring_boot.course.project_db.structure.aspects.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Log
@RestController
@RequestMapping("api/balloons")
public class BalloonController {

    private final BalloonService service;

    public BalloonController(BalloonService service) {
        this.service = service;
    }

    @GetMapping
    public List<Balloon> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Balloon> getById(@PathVariable int id) {
        Optional<Balloon> balloon = service.getByid(id);

        return balloon.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<Void> addBalloon(@RequestBody Balloon balloon) {
        service.addBalloon(balloon);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBalloon(@PathVariable int id, @RequestBody Balloon balloon) {
        balloon.setId(id);
        service.update(id, balloon);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBalloon(@PathVariable int id) {
        service.deleteBalloon(id);
        return ResponseEntity.noContent().build();
    }
}
