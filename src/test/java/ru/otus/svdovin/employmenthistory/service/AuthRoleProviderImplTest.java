package ru.otus.svdovin.employmenthistory.service;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import ru.otus.svdovin.employmenthistory.domain.AuthRole;
import ru.otus.svdovin.employmenthistory.dto.AuthRoleDto;
import ru.otus.svdovin.employmenthistory.repository.AuthRoleRepository;
import ru.otus.svdovin.employmenthistory.repository.AuthUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис для работы с ролями пользователей должен ")
@SpringBootTest(classes = {AuthRoleProviderImpl.class})
class AuthRoleProviderImplTest {

    private static final String AUTHROLE_NEW_ROLESYSNAME = "New RoleSysName";
    private static final String AUTHROLE_NEW_ROLENAME = "New RoleName";
    private static final long NEW_AUTHROLE_ID = 7;
    private static final long AUTHROLE_ID_EXIST = 3;

    @MockBean
    private AuthRoleRepository authRoleRepository;

    @MockBean
    private AuthUserRepository authUserRepository;

    @MockBean
    private MessageService messageService;

    @Autowired
    private AuthRoleProvider authRoleProvider;

    @Test
    @DisplayName("возвращать роль пользователей по id")
    void shouldReturnExpectedAuthRoleById() {
        val authRole = new AuthRole(AUTHROLE_ID_EXIST, AUTHROLE_NEW_ROLESYSNAME, AUTHROLE_NEW_ROLENAME);
        val authRoleDto = AuthRoleDto.buildDTO(authRole);
        Mockito.when(authRoleRepository.findById(NEW_AUTHROLE_ID)).thenReturn(Optional.of(authRole));
        AuthRoleDto actual = authRoleProvider.getAuthRole(NEW_AUTHROLE_ID);
        assertThat(actual).isEqualToComparingFieldByField(authRoleDto);
    }

    @Test
    @DisplayName("возвращать все роли пользователей")
    void shouldReturnAllAuthRoles() {
        val authRole = new AuthRole(AUTHROLE_ID_EXIST, AUTHROLE_NEW_ROLESYSNAME, AUTHROLE_NEW_ROLENAME);
        val authRoleDto = AuthRoleDto.buildDTO(authRole);
        List<AuthRole> list = new ArrayList<>();
        list.add(authRole);
        Mockito.when(authRoleRepository.findAll(Sort.by(Sort.Direction.ASC, "roleId"))).thenReturn(list);
        List<AuthRoleDto> listAuthRoles = authRoleProvider.getAuthRoleAll();
        assertAll("Сотрудник",
                () -> assertThat(listAuthRoles.size()).isEqualTo(1),
                () -> assertThat(listAuthRoles.get(0)).isEqualToComparingFieldByField(authRoleDto)
        );
    }

    @Test
    @DisplayName("изменять роль пользователей")
    void shouldUpdateExpectedAuthRole() {
        val authRole = new AuthRole(NEW_AUTHROLE_ID, AUTHROLE_NEW_ROLESYSNAME, AUTHROLE_NEW_ROLENAME);
        val authRoleDto = AuthRoleDto.buildDTO(authRole);
        Mockito.when(authRoleRepository.findById(NEW_AUTHROLE_ID)).thenReturn(Optional.of(authRole));
        authRoleProvider.updateAuthRole(authRoleDto);
        verify(authRoleRepository, times(1)).save(authRole);
    }
}