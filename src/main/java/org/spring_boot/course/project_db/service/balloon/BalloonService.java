package org.spring_boot.course.project_db.service.balloon;

import org.spring_boot.course.project_db.model.Balloon;
import org.spring_boot.course.project_db.repository.balloon.BalloonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BalloonService {
    private final BalloonRepository balloonRepository;

    public BalloonService(BalloonRepository balloonRepository) {
        this.balloonRepository = balloonRepository;
    }

    public Optional<Balloon> getByid(int id) {
        return balloonRepository.getById(id);
    }

    public List<Balloon> getAll() {
        return balloonRepository.getAll();
    }

    public int deleteBalloon(int id) {
        return balloonRepository.deleteById(id);
    }

    public int update(int id, Balloon updated) {
        return balloonRepository.updateBalloon(id, updated);
    }

    public Optional<Balloon> addBalloon(Balloon balloon) {
      return  balloonRepository.addBalloon(balloon);
    }
}
