package ru.otus.svdovin.employmenthistory.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.employmenthistory.domain.Record;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    boolean existsRecordByEmployeeEmployeeId(Long employeeId);

    boolean existsRecordByRecordTypeRecordTypeId(Long recordTypeId);

    @EntityGraph(value = "Record.recordType.employee")
    List<Record> findByEmployeeEmployeeId(Long employeeId, Sort sort);
}
