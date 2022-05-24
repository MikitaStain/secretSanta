package com.innowise.secret_santa.mapper;

import com.innowise.secret_santa.model.Role;
import com.innowise.secret_santa.model.dto.RoleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toRole(RoleDto roleDto);

    RoleDto toRoleDto(Role role);
}
