package ru.otus.svdovin.employmenthistory.service;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.svdovin.employmenthistory.domain.Company;
import ru.otus.svdovin.employmenthistory.dto.CompanyDto;
import ru.otus.svdovin.employmenthistory.repository.CompanyRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис для работы с компаниями должен ")
@SpringBootTest(classes = {CompanyProviderImpl.class})
class CompanyProviderImplTest {

    private static final long COMPANY_NEW_ID = 1;
    private static final String COMPANY_NEW_NAME = "New Name";
    private static final String COMPANY_NEW_INN = "New Inn";
    private static final String COMPANY_NEW_KPP = "New Kpp";
    private static final String COMPANY_NEW_PFR = "New Pfr";
    private static final String COMPANY_NEW_POSITION = "New Posision";
    private static final String COMPANY_NEW_CHIEF_FIO = "New Chief Fio";

    @MockBean
    private CompanyRepository companyRepository;

    @MockBean
    private MessageService messageService;

    @Autowired
    private CompanyProvider companyProvider;

    @Test
    @DisplayName("возвращать ожидаемое предприятие по его id")
    void shouldReturnExpectedCompanyById() {
        val company = new Company(COMPANY_NEW_ID, COMPANY_NEW_NAME, COMPANY_NEW_INN,
                COMPANY_NEW_KPP, COMPANY_NEW_PFR, COMPANY_NEW_POSITION, COMPANY_NEW_CHIEF_FIO);
        val companyDto = CompanyDto.buildDTO(company);
        Mockito.when(companyRepository.findById(COMPANY_NEW_ID)).thenReturn(Optional.of(company));
        CompanyDto actual = companyProvider.getCompany(COMPANY_NEW_ID);
        assertThat(actual).isEqualToComparingFieldByField(companyDto);
    }

    @Test
    @DisplayName("изменять ожидаемоге предприятие")
    void shouldUpdateExpectedCompany() {
        val company = new Company(COMPANY_NEW_ID, COMPANY_NEW_NAME, COMPANY_NEW_INN,
                COMPANY_NEW_KPP, COMPANY_NEW_PFR, COMPANY_NEW_POSITION, COMPANY_NEW_CHIEF_FIO);
        val companyDto = CompanyDto.buildDTO(company);
        Mockito.when(companyRepository.findById(COMPANY_NEW_ID)).thenReturn(Optional.of(company));
        companyProvider.updateCompany(companyDto);
        verify(companyRepository, times(1)).save(company);
    }
}