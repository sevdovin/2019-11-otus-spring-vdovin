package ru.otus.svdovin.employmenthistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.svdovin.employmenthistory.dto.AuthUserDto;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthUserProvider authUserProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUserDto userDto = authUserProvider.getAuthUserByLogin(username);
        return new org.springframework.security.core.userdetails.User(userDto.getLogin(), userDto.getPassword(),
                getAuthorities(userDto.getRoleSysName()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String roleSysName) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (roleSysName != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleSysName));
        }
        return authorities;
    }
}
