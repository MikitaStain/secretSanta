package com.innowise.secret_santa.util;

import com.innowise.secret_santa.exception.NoAccessException;
import com.innowise.secret_santa.exception.NoDataFoundException;
import com.innowise.secret_santa.security.UserSecurity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public final class HandleAuthorities {

    private HandleAuthorities() {
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
}