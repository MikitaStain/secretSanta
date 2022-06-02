package com.innowise.secret_santa.model.dto.response_dto;

import com.innowise.secret_santa.model.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountAuthenticationResponse {

    private Long id;

    private String email;

    private String password;

    private RoleDto role;
}