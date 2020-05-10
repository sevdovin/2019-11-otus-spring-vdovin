package ru.otus.svdovin.employmenthistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.employmenthistory.domain.AuthRole;

@Repository
public interface AuthRoleRepository extends JpaRepository<AuthRole, Long> {

}
