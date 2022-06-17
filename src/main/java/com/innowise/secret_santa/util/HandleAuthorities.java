package com.innowise.secret_santa.util;

import com.innowise.secret_santa.exception.NoAccessException;
import com.innowise.secret_santa.exception.NoDataFoundException;
import com.innowise.secret_santa.security.UserSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class HandleAuthorities {

    private static final String ROLE_NAME_FOR_COMPARE = "ROLE_ADMIN";

    private HandleAuthorities() {
    }

    public static boolean comparePrincipalIdAndAuthenticationId(Long id) {

        return getPrincipal().getId().equals(id);
    }

    public static boolean comparePrincipalRolesAndAuthenticationRoles() {

        return getRolesPrincipal().contains(ROLE_NAME_FOR_COMPARE);
    }

    public static Long getIdAuthenticationAccount(){
        return Optional.ofNullable(getPrincipal())
                .map(UserSecurity::getId)
                .orElseThrow(()-> new NoAccessException("Unknown identification account"));
    }

    private static UserSecurity getPrincipal() {
        return Optional
                .ofNullable
                        ((UserSecurity) SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal())
                .orElseThrow(() -> new NoDataFoundException("You don't authentication"));
    }

    private static List<String> getRolesPrincipal() {

        return getPrincipal()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}