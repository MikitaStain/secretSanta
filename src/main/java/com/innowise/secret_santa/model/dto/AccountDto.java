package com.innowise.secret_santa.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {


    private String email;

    private String password;

    private LocalDateTime dateCreated;

    private RoleDto role;
}
