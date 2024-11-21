package org.spring_boot.course.project_db.service;

import org.spring_boot.course.project_db.model.User;

public interface UserService {

    User register(User user);

    User login(User user);



}
