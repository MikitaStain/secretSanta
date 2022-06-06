package com.innowise.secret_santa.security;

import com.innowise.secret_santa.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    private final AccountService service;

    @Autowired
    public UserSecurityService(AccountService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return UserSecurityFactory.addUserSecurity(service.getAccountAuthByEmail(email));
    }
}