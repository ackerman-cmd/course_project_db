package org.spring_boot.course.project_db.controller.user;

import org.spring_boot.course.project_db.model.User;
import org.spring_boot.course.project_db.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getById(id).orElse(null);
    }

    @GetMapping("/username/{username}")
    public User getUserByName(@PathVariable String username) {
        return userService.getByName(username).orElse(null);
    }

    @GetMapping
    public List<User> getAllUsers(@RequestParam(required = false) String role) {
        return userService.getAll(role);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User user) {
        userService.updateUser(id, user);
    }
}
