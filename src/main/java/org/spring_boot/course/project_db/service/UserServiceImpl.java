package org.spring_boot.course.project_db.service;

import org.spring_boot.course.project_db.model.User;
import org.spring_boot.course.project_db.repository.UserDAO;
import org.spring_boot.course.project_db.utils.exeptions.DuplicateNameUserExc;
import org.spring_boot.course.project_db.utils.exeptions.DuplicatePasswordExc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        if (userDAO.getByName(user.getUserName()) != null) {
            throw new DuplicateNameUserExc("Пользователь с именем " + user.getUserName() + " уже существует ");
        }

        String hashedPassword = hashPassword(user.getPasswordHash());

        if (userDAO.allUsersWithTheSamePassword(hashedPassword).size() > 1) {
            throw new DuplicatePasswordExc("Этот пароль уже используется");
        }

        user.setPasswordHash(hashedPassword);


       return userDAO.save(user);
    }

    @Override
    public User login(User user) {
        User foundUser = userDAO.getByName(user.getUserName());
        if (foundUser == null || !passwordEncoder.matches(user.getPasswordHash(), foundUser.getPasswordHash())) {
            throw new IllegalArgumentException("Неверное имя пользователя или пароль");
        }
        return foundUser;
    }

    private String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }


}
