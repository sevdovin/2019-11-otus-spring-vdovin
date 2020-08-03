package ru.otus.svdovin.homework22.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import ru.otus.svdovin.homework22.domain.AuthRole;
import ru.otus.svdovin.homework22.domain.AuthUser;
import ru.otus.svdovin.homework22.repository.AuthUserRepository;

@SpringBootTest(classes = {UserDetailsServiceImpl.class})
@DisplayName("Testing UserDetailsService")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserDetailsServiceImplTest {

    private static final String ADMINISTRATOR = "administrator";

    private static final String USER_NAME = "admin";
    
    private static final String PASSWORD = "123";
    
    private AuthUser admin;
    private AuthRole role;
    
    @MockBean
    private AuthUserRepository authUserRepository;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @BeforeEach
    public void init() {
        role = AuthRole.builder().roleId(Long.valueOf(1000)).roleSysName(ADMINISTRATOR).build();
        admin = AuthUser.builder().username(USER_NAME).password(PASSWORD).authRole(role).build();
        when(authUserRepository.findByUsername(USER_NAME)).thenReturn(admin);
    }
    
    @Test
    public void loadUserByUsername() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(USER_NAME);
        assertThat(userDetails).isEqualToComparingOnlyGivenFields(admin, "username", "password");
        assertThat(userDetails.getAuthorities()).isEqualTo(
                Stream.of(new SimpleGrantedAuthority(role.getRoleSysName())).collect(Collectors.toSet()));
    }
}
