package ru.otus.svdovin.employmenthistory.service;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import ru.otus.svdovin.employmenthistory.domain.Company;
import ru.otus.svdovin.employmenthistory.domain.Employee;
import ru.otus.svdovin.employmenthistory.dto.EmployeeDto;
import ru.otus.svdovin.employmenthistory.repository.CompanyRepository;
import ru.otus.svdovin.employmenthistory.repository.EmployeeRepository;
import ru.otus.svdovin.employmenthistory.repository.RecordRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис для работы с сотрудниками должен ")
@SpringBootTest(classes = {EmployeeProviderImpl.class})
class EmployeeProviderImplTest {

    private static final String EMPLOYEE_NEW_LASTNAME = "New LastName";
    private static final String EMPLOYEE_NEW_FIRSTNAME = "New FirstName";
    private static final String EMPLOYEE_NEW_MIDDLENAME = "New MiddleName";
    private static final LocalDate EMPLOYEE_NEW_BIRTHDAY = LocalDate.now();
    private static final String EMPLOYEE_NEW_SNILS = "New Snils";
    private static final long COMPANY_NEW_ID = 1;
    private static final String COMPANY_NEW_NAME = "New Company";
    private static final long NEW_EMPLOYEE_ID = 7;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private CompanyRepository companyRepository;

    @MockBean
    private RecordRepository recordRepository;

    @MockBean
    private MessageService messageService;

    @Autowired
    private EmployeeProvider employeeProvider;

    @Test
    @DisplayName("возвращать ожидаемого сотрудника по его id")
    void shouldReturnExpectedEmployeeById() {
        val employee = new Employee(NEW_EMPLOYEE_ID, EMPLOYEE_NEW_LASTNAME, EMPLOYEE_NEW_FIRSTNAME,
                EMPLOYEE_NEW_MIDDLENAME, EMPLOYEE_NEW_BIRTHDAY, EMPLOYEE_NEW_SNILS);
        val employeeDto = EmployeeDto.buildDTO(employee);
        Mockito.when(employeeRepository.findById(NEW_EMPLOYEE_ID)).thenReturn(Optional.of(employee));
        EmployeeDto actual = employeeProvider.getEmployee(NEW_EMPLOYEE_ID);
        assertThat(actual).isEqualToComparingFieldByField(employeeDto);
    }

    @Test
    @DisplayName("возвращать всех сотрудников")
    void shouldReturnAllEmployees() {
        val employee = new Employee(NEW_EMPLOYEE_ID, EMPLOYEE_NEW_LASTNAME, EMPLOYEE_NEW_FIRSTNAME,
                EMPLOYEE_NEW_MIDDLENAME, EMPLOYEE_NEW_BIRTHDAY, EMPLOYEE_NEW_SNILS);
        val employeeDto = EmployeeDto.buildDTO(employee);
        List<Employee> list = new ArrayList<>();
        list.add(employee);
        Mockito.when(employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "lastName").and(
                Sort.by(Sort.Direction.ASC, "firstName")))).thenReturn(list);
        List<EmployeeDto> listEmployees = employeeProvider.getEmployeeAll();
        assertAll("Сотрудник",
                () -> assertThat(listEmployees.size()).isEqualTo(1),
                () -> assertThat(listEmployees.get(0)).isEqualToComparingFieldByField(employeeDto)
        );
    }

    @Test
    @DisplayName("должен корректно добавлять сотрудника")
    void shouldCeateEmployee() {
        val company = new Company(COMPANY_NEW_ID, COMPANY_NEW_NAME, null, null,
                null, null, null);
        val employee = new Employee(NEW_EMPLOYEE_ID, EMPLOYEE_NEW_LASTNAME, EMPLOYEE_NEW_FIRSTNAME,
                EMPLOYEE_NEW_MIDDLENAME, EMPLOYEE_NEW_BIRTHDAY, EMPLOYEE_NEW_SNILS, company);
        val employee2 = new Employee(0L, EMPLOYEE_NEW_LASTNAME, EMPLOYEE_NEW_FIRSTNAME,
                EMPLOYEE_NEW_MIDDLENAME, EMPLOYEE_NEW_BIRTHDAY, EMPLOYEE_NEW_SNILS, company);
        val employeeDto = EmployeeDto.buildDTO(employee);
        Mockito.when(companyRepository.findById(COMPANY_NEW_ID)).thenReturn(Optional.of(company));
        Mockito.when(employeeRepository.save(employee2)).thenReturn(employee);
        long newId = employeeProvider.createEmployee(employeeDto);
        assertThat(newId).isEqualTo(NEW_EMPLOYEE_ID);
    }

    @Test
    @DisplayName("изменять ожидаемого сотрудника")
    void shouldUpdateExpectedEmployee() {
        val employee = new Employee(NEW_EMPLOYEE_ID, EMPLOYEE_NEW_LASTNAME, EMPLOYEE_NEW_FIRSTNAME,
                EMPLOYEE_NEW_MIDDLENAME, EMPLOYEE_NEW_BIRTHDAY, EMPLOYEE_NEW_SNILS);
        val employeeDto = EmployeeDto.buildDTO(employee);
        Mockito.when(employeeRepository.findById(NEW_EMPLOYEE_ID)).thenReturn(Optional.of(employee));
        employeeProvider.updateEmployee(employeeDto);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    @DisplayName("удалять ожидаемого сотрудника")
    void shouldDeleteExpectedEmployeeById() {
        val employee = new Employee(NEW_EMPLOYEE_ID, EMPLOYEE_NEW_LASTNAME, EMPLOYEE_NEW_FIRSTNAME,
                EMPLOYEE_NEW_MIDDLENAME, EMPLOYEE_NEW_BIRTHDAY, EMPLOYEE_NEW_SNILS);
        Mockito.when(employeeRepository.findById(NEW_EMPLOYEE_ID)).thenReturn(Optional.of(employee));
        Mockito.when(recordRepository.existsRecordByEmployeeEmployeeId(NEW_EMPLOYEE_ID)).thenReturn(false);
        employeeProvider.deleteEmployee(NEW_EMPLOYEE_ID);
        verify(employeeRepository, times(1)).deleteById(NEW_EMPLOYEE_ID);
    }
}