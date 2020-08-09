package ru.otus.svdovin.homework24.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.otus.svdovin.homework24.domain.AclSid;

@Repository
public interface AclSidRepository extends JpaRepository<AclSid, Long> {
    
    Optional<AclSid> findBySid(String sid);
}
