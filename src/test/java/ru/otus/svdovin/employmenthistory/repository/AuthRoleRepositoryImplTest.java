package ru.otus.svdovin.employmenthistory.repository;

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
