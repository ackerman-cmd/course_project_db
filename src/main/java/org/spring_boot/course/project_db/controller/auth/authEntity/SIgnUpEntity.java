package org.spring_boot.course.project_db.controller.auth.authEntity;

import org.spring_boot.course.project_db.model.Role;

public record SIgnUpEntity(
        String username,
        String fullName,
        String password,
        Role role
) {
}
