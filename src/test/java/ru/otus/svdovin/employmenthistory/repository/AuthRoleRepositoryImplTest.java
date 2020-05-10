package ru.otus.svdovin.employmenthistory.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.svdovin.employmenthistory.domain.AuthRole;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с ролями пользователей должен ")
@DataJpaTest
class AuthRoleRepositoryImplTest {

    private static final String AUTHROLE_NEW_ROLESYSNAME = "New RoleSysName";
    private static final String AUTHROLE_NEW_ROLENAME = "New RoleName";
    private static final long AUTHROLE_ID_EXIST = 3;
    private static final String AUTHROLE_ROLENAME_EXISTS = "Сотрудник";
    private static final int AUTHROLE_COUNT_INT = 3;

    @Autowired
    private AuthRoleRepository authRoleRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять роль пользователей")
    @Test
    void shouldInsertAuthRole() {
        val authRole = new AuthRole(0, AUTHROLE_NEW_ROLESYSNAME, AUTHROLE_NEW_ROLENAME);
        long newId = authRoleRepository.save(authRole).getRoleId();
        authRole.setRoleId(newId);
        val actual = em.find(AuthRole.class, newId);
        assertThat(actual).isNotNull().isEqualToComparingFieldByField(authRole);
    }

    @DisplayName("изменять роль пользователей")
    @Test
    void shouldUpdateAuthRoleName() {
        AuthRole authRole1 = em.find(AuthRole.class, AUTHROLE_ID_EXIST);
        String oldName = authRole1.getRoleName();
        em.detach(authRole1);
        authRole1.setRoleName(AUTHROLE_NEW_ROLENAME);
        AuthRole authRole2 = authRoleRepository.save(authRole1);
        assertThat(authRole2.getRoleName()).isNotEqualTo(oldName).isEqualTo(AUTHROLE_NEW_ROLENAME);
    }

    @DisplayName("удалять роль пользователей")
    @Test
    void shouldDeleteAuthRole() {
        val authRole1 = em.find(AuthRole.class, AUTHROLE_ID_EXIST);
        assertThat(authRole1).isNotNull();
        em.detach(authRole1);
        authRoleRepository.deleteById(AUTHROLE_ID_EXIST);
        val authRole2 = em.find(AuthRole.class, AUTHROLE_ID_EXIST);
        assertThat(authRole2).isNull();
    }

    @DisplayName("возвращать роль пользователей по id")
    @Test
    void shouldFindAuthRoleById() {
        Optional<AuthRole> authRole = authRoleRepository.findById(AUTHROLE_ID_EXIST);
        assertThat(authRole).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("roleName", AUTHROLE_ROLENAME_EXISTS);
    }

    @DisplayName("возвращать все роли пользователей")
    @Test
    void shouldFindEmployeeAll() {
        List<AuthRole> employees = authRoleRepository.findAll();
        assertThat(employees).isNotNull().hasSize(AUTHROLE_COUNT_INT);
    }
}
