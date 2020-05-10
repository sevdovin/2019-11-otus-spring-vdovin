package ru.otus.svdovin.employmenthistory.service;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import ru.otus.svdovin.employmenthistory.domain.AuthUser;
import ru.otus.svdovin.employmenthistory.dto.AuthUserDto;
import ru.otus.svdovin.employmenthistory.repository.AuthUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис для работы с пользователями должен ")
@SpringBootTest(classes = {AuthUserProviderImpl.class})
class AuthUserProviderImplTest {

    private static final Long AUTHUSER_NEW_EMPLOYEEID = 1L;
    private static final String AUTHUSER_NEW_LOGIN = "New Login";
    private static final String AUTHUSER_NEW_PASSWORD = "New Password";
    private static final Boolean AUTHUSER_NEW_ISENABLED = false;
    private static final String AUTHUSER_NEW_EMAIL = "New Email";
    private static final long NEW_AUTHUSER_ID = 7;
    private static final long AUTHUSER_ID_EXIST = 3;

    @MockBean
    private AuthUserRepository authUserRepository;

    @MockBean
    private MessageService messageService;

    @Autowired
    private AuthUserProvider authUserProvider;

    @Test
    @DisplayName("возвращать пользователя по id")
    void shouldReturnExpectedAuthUserById() {
        val authUser = new AuthUser(AUTHUSER_ID_EXIST, AUTHUSER_NEW_EMPLOYEEID, AUTHUSER_NEW_LOGIN, AUTHUSER_NEW_PASSWORD,
                AUTHUSER_NEW_ISENABLED, AUTHUSER_NEW_EMAIL);
        val authUserDto = authUser.buildDTO();
        Mockito.when(authUserRepository.findById(NEW_AUTHUSER_ID)).thenReturn(Optional.of(authUser));
        AuthUserDto actual = authUserProvider.getAuthUser(NEW_AUTHUSER_ID);
        assertThat(actual).isEqualToComparingFieldByField(authUserDto);
    }

    @Test
    @DisplayName("возвращать всех пользователей")
    void shouldReturnAllAuthUsers() {
        val authUser = new AuthUser(AUTHUSER_ID_EXIST, AUTHUSER_NEW_EMPLOYEEID, AUTHUSER_NEW_LOGIN, AUTHUSER_NEW_PASSWORD,
                AUTHUSER_NEW_ISENABLED, AUTHUSER_NEW_EMAIL);
        val authUserDto = authUser.buildDTO();
        List<AuthUser> list = new ArrayList<>();
        list.add(authUser);
        Mockito.when(authUserRepository.findAll(Sort.by(Sort.Direction.ASC, "userId"))).thenReturn(list);
        List<AuthUserDto> listAuthUsers = authUserProvider.getAuthUserAll();
        assertAll("Пользователь",
                () -> assertThat(listAuthUsers.size()).isEqualTo(1),
                () -> assertThat(listAuthUsers.get(0)).isEqualToComparingFieldByField(authUserDto)
        );
    }

    @Test
    @DisplayName("должен корректно добавлять пользователя")
    void shouldCeateAuthUser() {
        val authUser = new AuthUser(NEW_AUTHUSER_ID, AUTHUSER_NEW_EMPLOYEEID, AUTHUSER_NEW_LOGIN, AUTHUSER_NEW_PASSWORD,
                AUTHUSER_NEW_ISENABLED, AUTHUSER_NEW_EMAIL);
        val authUser2 = new AuthUser(0, AUTHUSER_NEW_EMPLOYEEID, AUTHUSER_NEW_LOGIN, AUTHUSER_NEW_PASSWORD,
                AUTHUSER_NEW_ISENABLED, AUTHUSER_NEW_EMAIL);
        val authUserDto = authUser.buildDTO();
        Mockito.when(authUserRepository.save(authUser2)).thenReturn(authUser);
        long newId = authUserProvider.createAuthUser(authUserDto);
        assertThat(newId).isEqualTo(NEW_AUTHUSER_ID);
    }

    @Test
    @DisplayName("изменять пользователя")
    void shouldUpdateExpectedAuthUser() {
        val authUser = new AuthUser(NEW_AUTHUSER_ID, AUTHUSER_NEW_EMPLOYEEID, AUTHUSER_NEW_LOGIN, AUTHUSER_NEW_PASSWORD,
                AUTHUSER_NEW_ISENABLED, AUTHUSER_NEW_EMAIL);
        val authUserDto = authUser.buildDTO();
        Mockito.when(authUserRepository.findById(NEW_AUTHUSER_ID)).thenReturn(Optional.of(authUser));
        authUserProvider.updateAuthUser(authUserDto);
        verify(authUserRepository, times(1)).save(authUser);
    }

    @Test
    @DisplayName("удалять ожидаемого пользователя")
    void shouldDeleteExpectedAuthUserById() {
        val authUser = new AuthUser(NEW_AUTHUSER_ID, AUTHUSER_NEW_EMPLOYEEID, AUTHUSER_NEW_LOGIN, AUTHUSER_NEW_PASSWORD,
                AUTHUSER_NEW_ISENABLED, AUTHUSER_NEW_EMAIL);
        Mockito.when(authUserRepository.findById(NEW_AUTHUSER_ID)).thenReturn(Optional.of(authUser));
        authUserProvider.deleteAuthUser(NEW_AUTHUSER_ID);
        verify(authUserRepository, times(1)).deleteById(NEW_AUTHUSER_ID);
    }

    @Test
    @DisplayName("изменять статус пользователя")
    void shouldUpdateEnabledAuthUser() {
        val authUser = new AuthUser(NEW_AUTHUSER_ID, AUTHUSER_NEW_EMPLOYEEID, AUTHUSER_NEW_LOGIN, AUTHUSER_NEW_PASSWORD,
                AUTHUSER_NEW_ISENABLED, AUTHUSER_NEW_EMAIL);
        Mockito.when(authUserRepository.findById(NEW_AUTHUSER_ID)).thenReturn(Optional.of(authUser));
        authUserProvider.changeEnabledAuthUser(NEW_AUTHUSER_ID, AUTHUSER_NEW_ISENABLED);
        verify(authUserRepository, times(1)).save(authUser);
    }
}