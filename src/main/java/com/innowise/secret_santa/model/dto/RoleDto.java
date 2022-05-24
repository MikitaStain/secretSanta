package com.innowise.secret_santa.model.dto;

import com.innowise.secret_santa.model.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {

    private RoleEnum roleEnum;
}
