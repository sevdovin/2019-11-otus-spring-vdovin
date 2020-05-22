package ru.otus.svdovin.employmenthistory.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.employmenthistory.domain.AuthUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    @EntityGraph(value = "AuthUser.authRole.employee")
    List<AuthUser> findAll();

    Optional<AuthUser> findByLogin(String login);
}
