package ru.otus.svdovin.homework22.service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.svdovin.homework22.domain.AuthRole;
import ru.otus.svdovin.homework22.domain.AuthUser;
import ru.otus.svdovin.homework22.repository.AuthUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AuthUserRepository authUserRepository;

    @Autowired
    public UserDetailsServiceImpl(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) {
        AuthUser applicationUser = authUserRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(applicationUser.getUsername(), applicationUser.getPassword(),
                getAuthorities(applicationUser.getAuthRole()));
                                                                                                                                                  
    }

    private Set<GrantedAuthority> getAuthorities(AuthRole role) {
        Set<GrantedAuthority> authoritySet = null;
        if (role != null) {
            authoritySet = Stream.of(new SimpleGrantedAuthority(role.getRoleSysName())).collect(Collectors.toSet());
        } else {
            authoritySet = Collections.<GrantedAuthority>emptySet();
        }
        return authoritySet;
    }

}
