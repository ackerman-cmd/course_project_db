package org.spring_boot.course.project_db.service;

import org.spring_boot.course.project_db.model.User;
import org.spring_boot.course.project_db.repository.UserDAO;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User register(String userName, String password, String role) {
        return null;
    }

    @Override
    public User login(String userName, String password) {
        return null;
    }
}
