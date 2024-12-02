package org.spring_boot.course.project_db.service.pilot;


import org.spring_boot.course.project_db.model.Pilot;
import org.spring_boot.course.project_db.repository.pilot.PilotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PilotService {

    private final PilotRepository pilotRepository;

    public PilotService(PilotRepository pilotRepository) {
        this.pilotRepository = pilotRepository;
    }

    public Optional<Pilot> getById(int id) {
        return pilotRepository.getPilot(id);
    }

    public List<Pilot> getAll() {
        return pilotRepository.getAll();
    }

    public Optional<Pilot> addPilot(Pilot pilot) {
      return  pilotRepository.save(pilot);
    }

    public boolean deletePilote(int id) {
        return pilotRepository.deletePilot(id);
    }

    public int updatePilot(int id, Pilot updated) {
        return pilotRepository.updatePilot(id, updated);
    }
}
