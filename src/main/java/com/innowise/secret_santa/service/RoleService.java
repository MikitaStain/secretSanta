package com.innowise.secret_santa.service;

import com.innowise.secret_santa.mapper.RoleMapper;
import com.innowise.secret_santa.model.Role;
import com.innowise.secret_santa.model.dto.RoleDto;
import com.innowise.secret_santa.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public void createdRole(RoleDto role){

        roleRepository.save(roleMapper.toRole(role));
    }

    public void deleteRole(Long id){
        roleRepository.deleteById(id);

    }

    public RoleDto getRoleById(Long id){
        return roleRepository.findById(id)
                .map(roleMapper::toRoleDto)
                .orElseThrow();
    }
}
