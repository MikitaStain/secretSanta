package com.innowise.secret_santa.service;

import com.innowise.secret_santa.exception.NoDataFoundException;
import com.innowise.secret_santa.security.UserSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoadIdAuthenticationAccountImpl implements LoadIdAuthenticationAccount{

    @Override
    public Long getAccountId() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        UserSecurity principal = (UserSecurity) authentication.getPrincipal();
        if (principal == null) {
            throw new NoDataFoundException("You don't authentication");
        }
        return principal.getId();
    }
}