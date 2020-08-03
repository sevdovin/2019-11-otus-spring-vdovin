package ru.otus.svdovin.homework22.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.otus.svdovin.homework22.domain.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    AuthUser findByUsername(String username);
}
