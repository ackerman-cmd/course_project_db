package org.spring_boot.course.project_db.service;

import org.spring_boot.course.project_db.model.User;

public interface UserService {

    User register(String userName, String password, String role);

    User login(String userName, String password);



}
