package org.spring_boot.course.project_db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int userId;

    private String userName;

    private String passwordHash;

    private Set<Role> role;

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", role=" + role +
                '}';
    }
}
