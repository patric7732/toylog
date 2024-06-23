package org.example.toylog.domain.user.entity;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("admin"), ROLE_USER("user");
    String Description;

    Role(String description) {
        Description = description;
    }
}
