package com.innowise.secret_santa.mapper;

import com.innowise.secret_santa.model.postgres.Role;
import com.innowise.secret_santa.model.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "id", ignore = true)
    Role toRole(RoleDto roleDto);

    RoleDto toRoleDto(Role role);

}
