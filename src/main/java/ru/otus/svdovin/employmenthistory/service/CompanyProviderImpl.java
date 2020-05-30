package ru.otus.svdovin.employmenthistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.svdovin.employmenthistory.domain.Company;
import ru.otus.svdovin.employmenthistory.dto.CompanyDto;
import ru.otus.svdovin.employmenthistory.exception.APIException;
import ru.otus.svdovin.employmenthistory.exception.ErrorCode;
import ru.otus.svdovin.employmenthistory.repository.CompanyRepository;

@RequiredArgsConstructor
@Service
public class CompanyProviderImpl implements CompanyProvider {

    private final CompanyRepository companyRepository;
    private final MessageService messageService;

    @Transactional(readOnly = true)
    @Override
    public CompanyDto getCompany(long companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null) {
            throw new APIException(
                    messageService.getFormatedMessage("employmentHistory.InvalidCompanyId", companyId),
                    ErrorCode.INVALID_COMPANY_ID.getCode()
            );
        }
        return CompanyDto.buildDTO(company);
    }

    @Transactional
    @Override
    public void updateCompany(CompanyDto companyDto) {
        Company company = companyRepository.findById(companyDto.getCompanyId()).orElse(null);
        if (company == null) {
            throw new APIException(
                    messageService.getMessage("eploymentHistory.CompanyNotFound"),
                    ErrorCode.COMPANY_NOT_FOUND.getCode()
            );
        }
        if (companyDto.getCompanyName() != null) company.setCompanyName(companyDto.getCompanyName());
        if (companyDto.getCompanyInn() != null) company.setCompanyInn(companyDto.getCompanyInn());
        if (companyDto.getCompanyKpp() != null) company.setCompanyKpp(companyDto.getCompanyKpp());
        if (companyDto.getCompanyPfr() != null) company.setCompanyPfr(companyDto.getCompanyPfr());
        if (companyDto.getChiefPosition() != null) company.setChiefPosition(companyDto.getChiefPosition());
        if (companyDto.getChiefFio() != null) company.setChiefFio(companyDto.getChiefFio());
        companyRepository.save(company);
    }
}
