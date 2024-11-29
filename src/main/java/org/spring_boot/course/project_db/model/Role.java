package org.spring_boot.course.project_db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
public enum Role implements GrantedAuthority {
    ADMIN("admin"),
    USER("user"),
    EDITOR("editor");

    String role;


    @Override
    public String getAuthority() {
        return role;
    }
}
