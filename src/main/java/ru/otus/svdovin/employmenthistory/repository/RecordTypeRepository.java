package ru.otus.svdovin.employmenthistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.employmenthistory.domain.RecordType;

@Repository
public interface RecordTypeRepository extends JpaRepository<RecordType, Long> {

}
