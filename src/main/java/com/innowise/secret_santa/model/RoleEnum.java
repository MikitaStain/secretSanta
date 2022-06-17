package com.innowise.secret_santa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum RoleEnum {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER"),
    ROLE_ORGANIZER("ROLE_ORGANIZER");

    private String role;

    RoleEnum(String role) {
        this.role = role;
    }
}
