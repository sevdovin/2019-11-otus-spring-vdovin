package ru.otus.svdovin.employmenthistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.employmenthistory.domain.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    boolean existsAuthUserByAuthRoleRoleId(Long roleId);
}
