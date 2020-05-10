package ru.otus.svdovin.employmenthistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.svdovin.employmenthistory.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
