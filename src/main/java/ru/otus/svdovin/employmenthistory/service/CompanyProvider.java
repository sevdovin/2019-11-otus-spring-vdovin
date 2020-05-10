package ru.otus.svdovin.employmenthistory.service;

import ru.otus.svdovin.employmenthistory.dto.CompanyDto;

public interface CompanyProvider {
    CompanyDto getCompany(long companyId);
    void updateCompany(CompanyDto companyDto);
}
