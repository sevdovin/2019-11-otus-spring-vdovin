package ru.otus.svdovin.homework22.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.otus.svdovin.homework22.domain.AuthRole;

@Repository
public interface AuthRoleRepository extends JpaRepository<AuthRole, Long> {
}
