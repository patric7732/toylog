package org.example.toylog.domain.user.entity;

import lombok.Getter;
@Getter
public enum Role {
    ROLE_USER("User"), ROLE_ADMIN("Admin");
    String description;

    Role(String description) {
        this.description = description;
    }
}
