package org.spring_boot.course.project_db.service.user;

import org.spring_boot.course.project_db.model.User;
import org.spring_boot.course.project_db.repository.user.UserRepository;
import org.spring_boot.course.project_db.structure.aspects.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll(String role) {
        return userRepository.findAll(role);
    }

    public Optional<User> getByName(String name) {
        return userRepository.findByUsername(name);
    }

    public Optional<User> getById(int id) {
        return userRepository.findById(id);
    }

    public int updateUser (int id, User updated) {
        return userRepository.updateUser(id, updated);
    }
}
