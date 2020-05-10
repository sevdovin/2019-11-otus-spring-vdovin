package ru.otus.svdovin.employmenthistory.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.svdovin.employmenthistory.domain.Company;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с компаниями должен ")
@DataJpaTest
class CompanyRepositoryImplTest {

    private static final String COMPANY_NEW_NAME = "New Name";
    private static final String COMPANY_NEW_INN = "New Inn";
    private static final String COMPANY_NEW_KPP = "New Kpp";
    private static final String COMPANY_NEW_PFR = "New Pfr";
    private static final String COMPANY_NEW_POSITION = "New Posision";
    private static final String COMPANY_NEW_CHIEF_FIO = "New Chief Fio";
    private static final long COMPANY_ID_EXIST = 1;
    private static final String COMPANY_NAME_EXISTS = "ООО \"Рога и копыта\"";
    private static final int COMPANYS_COUNT_INT = 1;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять компанию в БД")
    @Test
    void shouldInsertCompany() {
        val company = new Company(0L, COMPANY_NEW_NAME, COMPANY_NEW_INN,
                COMPANY_NEW_KPP, COMPANY_NEW_PFR, COMPANY_NEW_POSITION, COMPANY_NEW_CHIEF_FIO);
        long newId = companyRepository.save(company).getCompanyId();
        company.setCompanyId(newId);
        val actual = em.find(Company.class, newId);
        assertThat(actual).isNotNull().isEqualToComparingFieldByField(company);
    }

    @DisplayName("изменять наименование компании")
    @Test
    void shouldUpdateCompanyName() {
        Company company1 = em.find(Company.class, COMPANY_ID_EXIST);
        String oldName = company1.getCompanyName();
        em.detach(company1);
        company1.setCompanyName(COMPANY_NEW_NAME);
        Company company2 = companyRepository.save(company1);
        assertThat(company2.getCompanyName()).isNotEqualTo(oldName).isEqualTo(COMPANY_NEW_NAME);
    }

    @DisplayName("удалять компанию")
    @Test
    void shouldDeleteCompany() {
        val company1 = em.find(Company.class, COMPANY_ID_EXIST);
        assertThat(company1).isNotNull();
        em.detach(company1);
        companyRepository.deleteById(COMPANY_ID_EXIST);
        val company2 = em.find(Company.class, COMPANY_ID_EXIST);
        assertThat(company2).isNull();
    }

    @DisplayName("возвращать компанию по ее id")
    @Test
    void shouldFindCompanyById() {
        Optional<Company> company = companyRepository.findById(COMPANY_ID_EXIST);
        assertThat(company).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("companyName", COMPANY_NAME_EXISTS);
    }

    @DisplayName("возвращать список всех компаний")
    @Test
    void shouldFindCompanyAll() {
        List<Company> companys = companyRepository.findAll();
        assertThat(companys).isNotNull().hasSize(COMPANYS_COUNT_INT);
    }
}
