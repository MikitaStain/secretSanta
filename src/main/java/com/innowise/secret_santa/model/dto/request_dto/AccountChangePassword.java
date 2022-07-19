package com.innowise.secret_santa.model.dto.request_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountChangePassword {

    private String oldPassword;

    private String newPassword;

    private String newPassword2;
}
