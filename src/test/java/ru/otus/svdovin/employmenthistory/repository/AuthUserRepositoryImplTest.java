package ru.otus.svdovin.employmenthistory.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.svdovin.employmenthistory.domain.AuthUser;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с пользователями должен ")
@DataJpaTest
class AuthUserRepositoryImplTest {

    private static final String AUTHUSER_NEW_LOGIN = "New Login";
    private static final String AUTHUSER_NEW_PASSWORD = "New Password";
    private static final Boolean AUTHUSER_NEW_ISENABLED = false;
    private static final String AUTHUSER_NEW_EMAIL = "New Email";
    private static final long AUTHUSER_ID_EXIST = 1;
    private static final String AUTHUSER_EMAIL_EXISTS = "svdovin@diasoft.ru";
    private static final int AUTHUSER_COUNT_INT = 4;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять пользователя")
    @Test
    void shouldInsertAuthUser() {
        val authUser = new AuthUser(0, AUTHUSER_NEW_LOGIN, AUTHUSER_NEW_PASSWORD,
                AUTHUSER_NEW_ISENABLED, AUTHUSER_NEW_EMAIL);
        long newId = authUserRepository.save(authUser).getUserId();
        authUser.setUserId(newId);
        val actual = em.find(AuthUser.class, newId);
        assertThat(actual).isNotNull().isEqualToComparingFieldByField(authUser);
    }

    @DisplayName("изменять пользователя")
    @Test
    void shouldUpdateAuthUserName() {
        AuthUser authUser1 = em.find(AuthUser.class, AUTHUSER_ID_EXIST);
        String oldEmail = authUser1.getEmail();
        em.detach(authUser1);
        authUser1.setEmail(AUTHUSER_NEW_EMAIL);
        AuthUser authUser2 = authUserRepository.save(authUser1);
        assertThat(authUser2.getEmail()).isNotEqualTo(oldEmail).isEqualTo(AUTHUSER_NEW_EMAIL);
    }

    @DisplayName("удалять пользователя")
    @Test
    void shouldDeleteAuthUser() {
        val authUser1 = em.find(AuthUser.class, AUTHUSER_ID_EXIST);
        assertThat(authUser1).isNotNull();
        em.detach(authUser1);
        authUserRepository.deleteById(AUTHUSER_ID_EXIST);
        val authUser2 = em.find(AuthUser.class, AUTHUSER_ID_EXIST);
        assertThat(authUser2).isNull();
    }

    @DisplayName("возвращать пользователя по id")
    @Test
    void shouldFindAuthUserById() {
        Optional<AuthUser> authUser = authUserRepository.findById(AUTHUSER_ID_EXIST);
        assertThat(authUser).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("email", AUTHUSER_EMAIL_EXISTS);
    }

    @DisplayName("возвращать всех пользователей")
    @Test
    void shouldFindAuthUserAll() {
        List<AuthUser> authUsers = authUserRepository.findAll();
        assertThat(authUsers).isNotNull().hasSize(AUTHUSER_COUNT_INT);
    }

    @DisplayName("изменять статус пользователя")
    @Test
    void shouldUpdateEnabledAuthUser() {
        AuthUser authUser1 = em.find(AuthUser.class, AUTHUSER_ID_EXIST);
        Boolean oldIsEnabled = authUser1.getIsEnabled();
        em.detach(authUser1);
        authUser1.setIsEnabled(AUTHUSER_NEW_ISENABLED);
        AuthUser authUser2 = authUserRepository.save(authUser1);
        assertThat(authUser2.getIsEnabled()).isNotEqualTo(oldIsEnabled).isEqualTo(AUTHUSER_NEW_ISENABLED);
    }
}
